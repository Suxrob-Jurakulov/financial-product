package com.company.service;

import com.company.dto.AmountDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.company.util.Constants.COMMISSION_FEE;

@Slf4j
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public Long calculateCommissionAmount(double commissionFee, Long requestAmount) {
        log.info("Calculating commission amount : {}", commissionFee);

        return Math.round(requestAmount * (commissionFee / 100));
    }

    @Override
    public AmountDetailsDto calculateTransactionAmount(Long requestAmount) {
        Long commissionAmount = calculateCommissionAmount(COMMISSION_FEE, requestAmount);
        Long transactionAmount = requestAmount + commissionAmount;

        return new AmountDetailsDto(transactionAmount, commissionAmount, requestAmount);
    }
}
