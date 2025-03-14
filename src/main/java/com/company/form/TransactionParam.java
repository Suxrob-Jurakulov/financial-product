package com.company.form;

import com.company.domain.transactions.TransactionType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionParam {

    @NotNull(message = "Card number cannot be null")
    @Digits(integer = 16, fraction = 0, message = "Must be a 16-digit number only")
    private String cardNumber;

    @NotNull(message = "Currency cannot be null")
    @Digits(integer = 3, fraction = 0, message = "Currency must be a 3-digit number only")
    private String ccy;

    @NotNull(message = "Type cannot be null")
    @Pattern(regexp = "CREDIT|DEBIT|CANCEL", message = "Invalid type value. Allowed types: CREDIT, DEBIT, CANCEL")
    private TransactionType type;
}
