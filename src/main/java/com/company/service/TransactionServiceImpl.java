package com.company.service;

import com.company.domain.transactions.Transaction;
import com.company.domain.transactions.TransactionStatus;
import com.company.dto.*;
import com.company.exp.CustomException;
import com.company.form.TransactionParamsForm;
import com.company.form.transactions.TransactionForm;
import com.company.repository.TransactionRepository;
import com.company.repository.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.company.util.Constants.COMMISSION_FEE;

/**
 * @author Sukhrob
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CardService cardService;
    private final ValidateService validateService;
    private final CalculateService calculateService;
    private final TransactionRepository repository;
    private final ExternalPaymentService externalPaymentService;
    private final TransactionSpecification transactionSpecification;

    @Override
    public PagingDto<TransactionDto> getByParams(TransactionParamsForm form) {
        List<Sort.Order> orders = List.of(Sort.Order.desc("createdDate"));

        // Dynamically merge specifications
        Specification<Transaction> spec = Specification.where(transactionSpecification.hasProfileId(form.getProfileId()));

        if ("debit".equals(form.getType())) {
            spec = spec.and(transactionSpecification.hasSenderCard(form.getType()));
        } else if ("credit".equals(form.getType())) {
            spec = spec.and(transactionSpecification.hasRecipientCard(form.getType()));
        }

        if (form.getStartDate() != null || form.getEndDate() != null) {
            spec = spec.and(transactionSpecification.hasDateRange(form.getStartDate(), form.getEndDate()));
        }

        // Get transaction
        Page<Transaction> transactions = repository.findAll(spec, PageRequest.of(form.getPage() - 1, form.getLimit(), Sort.by(orders)));

        // Create paging dto
        PagingDto<TransactionDto> paging = new PagingDto<>((int) transactions.getTotalElements(), form.getPage(), form.getLimit());
        paging.getItems().addAll(transactions.stream().map(this::map).toList());

        return paging;
    }

    @Override
    public TransactionDto getTransactionByProfile(String transactionId, String profileId) {
        Optional<Transaction> transactionOpl = repository.findByIdAndProfileId(transactionId, profileId);
        return transactionOpl.map(this::map).orElse(null);
    }

    @Override
    public TransactionDto doTransaction(TransactionForm form, TransactionStatus transactionStatus) {

        // Check sender card
        CardDto senderCard = cardService.checkByUser(form.getFromTransactionParam().getCardNumber(), form.getProfileId());
        if (!validateService.validateCheckPan(senderCard)) {
            throw new CustomException("Sender card is not valid");
        }

        // Check recipient card
        CardDto recipientCard = cardService.check(form.getToTransactionParam().getCardNumber());
        if (!validateService.validateCheckPan(recipientCard)) {
            throw new CustomException("Recipient card is not valid");
        }

        // Check currency
        if (Boolean.FALSE.equals(
                validateService.checkIsCurrencyUzs(
                        form.getFromTransactionParam().getCcy(), form.getToTransactionParam().getCcy()))) {
            throw new CustomException("Currency error");
        }

        // Check sender card amount
        if (!validateService.validateSenderCardAmount(senderCard, form.getAmount())) {
            log.error("Amount is insufficient");

            throw new CustomException("Sender card amount is insufficient");
        }

        AmountDetailsDto amountDetailsDto = calculateService.calculateTransactionAmount(form.getAmount());
        form.setAmount(amountDetailsDto.getRequestAmount());

        ExternalPaymentTransactionDto externalTransactionResponse = externalPaymentService.doExternalTransaction(form, transactionStatus);

        if (validateService.validateExternalPaymentTransactionResponse(externalTransactionResponse)) {
            try {
                externalPaymentService.exchangePayment(amountDetailsDto, form);
            } catch (CustomException e) {
                log.error("Error occurred while exchanging cards balance: {}", e.getMessage());

                //Save transaction with failed status
                setExternalTransactionStatus(externalTransactionResponse);
                Transaction failedTransaction = buildTransaction(form, externalTransactionResponse, amountDetailsDto);
                failedTransaction = repository.save(failedTransaction);

                log.info("Saved failed transaction: {}", failedTransaction);
                return map(failedTransaction);
            }
            log.info("Transaction performed successfully by external service: {}", externalTransactionResponse);
            Transaction savedTransaction = repository.save(buildTransaction(form, externalTransactionResponse, amountDetailsDto));

            log.info("Transaction created successfully: {}", savedTransaction);

            // Collect commission amount to General card
            cardService.creditCommissionCard(amountDetailsDto.getCommissionAmount());

            return map(savedTransaction);
        }
        log.error("External transaction failed: {}", externalTransactionResponse);
        setExternalTransactionStatus(externalTransactionResponse);

        Transaction failedTransaction = buildTransaction(form, externalTransactionResponse, amountDetailsDto);
        failedTransaction = repository.save(failedTransaction);
        log.info("Saved failed transaction: {}", failedTransaction);

        return map(failedTransaction);
    }

    private static void setExternalTransactionStatus(ExternalPaymentTransactionDto externalTransactionResponse) {
        externalTransactionResponse.setTransactionStatus(externalTransactionResponse.getTransactionStatus() == null ? TransactionStatus.FAILED : externalTransactionResponse.getTransactionStatus());
    }

    // Builder
    private Transaction buildTransaction(TransactionForm form, ExternalPaymentTransactionDto extPaymentTransactionDto, AmountDetailsDto amountDetails) {
        Transaction transaction = new Transaction();

        transaction.setId(UUID.randomUUID().toString());
        transaction.setSenderCard(extPaymentTransactionDto.getFromPan());
        transaction.setRecipientCard(extPaymentTransactionDto.getToPan());
        transaction.setProfileId(form.getProfileId());
        transaction.setExternalTransactionId(extPaymentTransactionDto.getExternalPaymentTransactionId());
        transaction.setTransactionStatus(extPaymentTransactionDto.getTransactionStatus());
        transaction.setSuccess(extPaymentTransactionDto.isSuccess());
        transaction.setRequestAmount(amountDetails.getRequestAmount());
        transaction.setCommissionAmount(amountDetails.getCommissionAmount());
        transaction.setCommissionFee(COMMISSION_FEE);
        transaction.setTransactionAmount(amountDetails.getTransactionAmount());
        transaction.setCreatedDate(LocalDateTime.now());

        return transaction;
    }

    // MAPPER
    private TransactionDto map(Transaction transaction) {
        TransactionDto dto = new TransactionDto();

        dto.setId(transaction.getId());
        dto.setSenderCard(transaction.getSenderCard());
        dto.setRecipientCard(transaction.getRecipientCard());
        dto.setProfileId(transaction.getProfileId());
        dto.setExternalTransactionId(transaction.getExternalTransactionId());
        dto.setTransactionStatus(transaction.getTransactionStatus().name());
        dto.setSuccess(transaction.getSuccess());
        dto.setRequestAmount(transaction.getRequestAmount());
        dto.setCommissionAmount(transaction.getCommissionAmount());
        dto.setCommissionFee(transaction.getCommissionFee());
        dto.setTransactionAmount(transaction.getTransactionAmount());
        dto.setCreatedDate(transaction.getCreatedDate());

        return dto;
    }
}
