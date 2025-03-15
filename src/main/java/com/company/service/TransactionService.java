package com.company.service;

import com.company.domain.transactions.TransactionStatus;
import com.company.dto.PagingDto;
import com.company.dto.TransactionDto;
import com.company.form.TransactionParamsForm;
import com.company.form.transactions.TransactionForm;

/**
 * @author Sukhrob
 */

public interface TransactionService {

    PagingDto<TransactionDto> getByParams(TransactionParamsForm form);

    TransactionDto getTransactionByProfile(String transactionId, String profileId);

    TransactionDto doTransaction(TransactionForm transactionForm, TransactionStatus transactionStatus);
}
