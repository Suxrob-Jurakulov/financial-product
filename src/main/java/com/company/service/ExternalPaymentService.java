package com.company.service;

import com.company.domain.transactions.TransactionStatus;
import com.company.dto.AmountDetailsDto;
import com.company.dto.ExternalPaymentTransactionDto;
import com.company.form.transactions.TransactionForm;

public interface ExternalPaymentService {

    ExternalPaymentTransactionDto doExternalTransaction(TransactionForm transactionForm, TransactionStatus transactionStatus);

    void exchangePayment(AmountDetailsDto amountDetails, TransactionForm transactionForm);
}
