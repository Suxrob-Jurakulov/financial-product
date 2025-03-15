package com.company.dto;

import com.company.domain.transactions.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalPaymentTransactionDto {

    private TransactionStatus transactionStatus;
    private String externalPaymentTransactionId;
    private String fromPan;
    private String toPan;
    private String recipientProfileId;
    private Long amount;
    private boolean success;
}
