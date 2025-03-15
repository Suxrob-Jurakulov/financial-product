package com.company.service;

import com.company.domain.transactions.TransactionStatus;
import com.company.dto.AmountDetailsDto;
import com.company.dto.ExternalPaymentTransactionDto;
import com.company.exp.CustomException;
import com.company.form.transactions.TransactionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalPaymentServiceImpl implements ExternalPaymentService {

    private final CardService cardService;

    @Override
    public ExternalPaymentTransactionDto doExternalTransaction(TransactionForm transactionForm, TransactionStatus transactionStatus) {
        log.info("Calling external payment service with {}, status {}", transactionForm, transactionStatus);
        if (Objects.equals(TransactionStatus.SUCCESS, transactionStatus)) {
            return getTransaction(transactionForm, transactionStatus);
        }
        if (Objects.equals(TransactionStatus.TIMEOUT, transactionStatus)) {
            try {
                Thread.sleep(5000);
            } catch (Exception ignore) {
                log.error("The transaction timed out {}", transactionForm);
            }
            return getTransaction(transactionForm, transactionStatus);
        }
        return getTransaction(transactionForm, TransactionStatus.FAILED);
    }

    @Override
    @Transactional
    public void exchangePayment(AmountDetailsDto amountDetails, TransactionForm form) {
        try {
            log.info("Calling external service for debit card: {}", form.getFromTransactionParam());
            cardService.debitCard(form.getFromTransactionParam().getCardNumber(), amountDetails.getTransactionAmount());
        } catch (Exception e) {
            log.error("Debit card failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        try {
            log.info("Calling external service for credit card: {}", form.getToTransactionParam());
            cardService.creditCard(form.getToTransactionParam().getCardNumber(), amountDetails.getRequestAmount());
        } catch (Exception e) {
            log.error("Credit card failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    private ExternalPaymentTransactionDto getTransaction(TransactionForm transactionForm, TransactionStatus status) {
        ExternalPaymentTransactionDto externalPaymentTransactionDto = new ExternalPaymentTransactionDto();

        externalPaymentTransactionDto.setAmount(transactionForm.getAmount());
        externalPaymentTransactionDto.setToPan(transactionForm.getToTransactionParam().getCardNumber());
        externalPaymentTransactionDto.setFromPan(transactionForm.getFromTransactionParam().getCardNumber());
        externalPaymentTransactionDto.setTransactionStatus(status);
        externalPaymentTransactionDto.setSuccess(Objects.equals(TransactionStatus.SUCCESS, status));
        externalPaymentTransactionDto.setExternalPaymentTransactionId(generateExternalTransactionId(status));

        return externalPaymentTransactionDto;
    }

    private String generateExternalTransactionId(TransactionStatus transactionStatus) {
        return switch (transactionStatus) {
            case SUCCESS, FAILED -> UUID.randomUUID().toString();
            case TIMEOUT -> null;
        };
    }
}
