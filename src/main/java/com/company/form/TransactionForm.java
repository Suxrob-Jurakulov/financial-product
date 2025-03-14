package com.company.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TransactionForm extends DefaultForm {

    @NotNull(message = "Amount cannot be null")
    private Long amount;

    @NotNull(message = "Sender info cannot be null")
    private TransactionParam fromTransactionParam;

    @NotNull(message = "Recipient info cannot be null")
    private TransactionParam toTransactionParam;
}
