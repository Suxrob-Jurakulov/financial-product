package com.company.service;

import com.company.dto.TransactionDto;
import com.company.form.TransactionForm;

/**
 * @author Sukhrob
 */

public interface TransactionService {

    TransactionDto doSuccessfulTransaction(TransactionForm transactionForm);
}
