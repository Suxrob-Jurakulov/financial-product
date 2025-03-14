package com.company.service;

import com.company.domain.transactions.TransactionStatus;
import com.company.dto.ExternalPaymentTransactionDto;
import com.company.form.TransactionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalPaymentServiceImpl implements ExternalPaymentService{

    @Override
    public ExternalPaymentTransactionDto createSuccessfulTransaction(TransactionForm transactionForm) {
        log.info("Calling external payment service with {}", transactionForm);
        return getSuccessfulTransaction(transactionForm);
    }

    private ExternalPaymentTransactionDto getSuccessfulTransaction(TransactionForm transactionForm) {
        ExternalPaymentTransactionDto externalPaymentTransactionDto = new ExternalPaymentTransactionDto();
        externalPaymentTransactionDto.setAmount(transactionForm.getAmount());
        externalPaymentTransactionDto.setToPan(transactionForm.getToTransactionParam().getCardNumber());
        externalPaymentTransactionDto.setFromPan(transactionForm.getFromTransactionParam().getCardNumber());
        externalPaymentTransactionDto.setTransactionStatus(TransactionStatus.SUCCESS);
        externalPaymentTransactionDto.setSuccess(true);
        externalPaymentTransactionDto.setExternalPaymentTransactionId(UUID.randomUUID().toString());
        return externalPaymentTransactionDto;
    }
}
