package com.company.service;

import com.company.dto.ExternalPaymentTransactionDto;
import com.company.form.TransactionForm;

public interface ExternalPaymentService {

    ExternalPaymentTransactionDto createSuccessfulTransaction(TransactionForm transactionForm);
}
