package com.company.service;

import com.company.dto.AmountDetailsDto;
import com.company.dto.CardDto;
import com.company.dto.ExternalPaymentTransactionDto;
import com.company.dto.TransactionDto;
import com.company.exp.CustomException;
import com.company.form.TransactionForm;
import com.company.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final TransactionRepository transactionRepository;
    private final ExternalPaymentService externalPaymentService;

    @Override
    public TransactionDto doSuccessfulTransaction(TransactionForm form) {
        /*
         * 1. need to get sender pan from our db
         * 2. is limit available on sender card
         * 3. validate external payment service response is ok
         * */

        CardDto senderCard = cardService.checkByUser(form.getFromTransactionParam().getCardNumber(), form.getProfileId());
        if (!validateService.validateCheckPan(senderCard)) {
            throw new CustomException("Sender card is not valid");
        }

        if (Boolean.FALSE.equals(
                validateService.checkIsCurrencyUzs(
                        form.getFromTransactionParam().getCcy(), form.getToTransactionParam().getCcy()))) {
            throw new CustomException("Currency error");
        }

        if (!validateService.validateSenderCardAmount(senderCard, form.getAmount())) {
            log.error("Amount is insufficient");

            throw new CustomException("Sender card amount is insufficient");
        }

        AmountDetailsDto amountDetailsDto = calculateService.calculateTransactionAmount(form.getAmount());
        form.setAmount(amountDetailsDto.getRequestAmount());

        ExternalPaymentTransactionDto externalTransactionResponse = externalPaymentService.createSuccessfulTransaction(form);

        if (!validateService.validateExternalPaymentTransactionResponse(externalTransactionResponse)) {
            //if status is null default FAILED
//            transactionRepository.save(toTransaction(externalTransactionResponse))
            //need to return error response
            //save also transaction on db with status
        }
        //9860600430005555 -> update balance + 2.0 commission fee (commissionAmount)
        log.info("Transaction performed successfully {}", externalTransactionResponse);
        //todo need to save on db external transaction response and return
        // need to all fields requestAmount, commissionAmount, transactionAmount -> amountDetailsDto
        return null;
    }

}
