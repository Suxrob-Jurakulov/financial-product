package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountDetailsDto {

    private Long transactionAmount;
    private Long commissionAmount;
    private Long requestAmount;
}
