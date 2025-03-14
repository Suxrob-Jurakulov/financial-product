package com.company.service;

import com.company.dto.AmountDetailsDto;

public interface CalculateService {

    Long calculateCommissionAmount(double commissionFee, Long requestAmount);

    AmountDetailsDto calculateTransactionAmount(Long requestAmount);
}
