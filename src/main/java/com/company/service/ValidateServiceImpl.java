package com.company.service;

import com.company.domain.cards.CardStatus;
import com.company.domain.transactions.TransactionStatus;
import com.company.dto.CardDto;
import com.company.dto.ExternalPaymentTransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.company.util.Constants.COMMISSION_FEE;
import static com.company.util.Constants.UZS_CCY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final CalculateService calculateService;

    @Override
    public boolean checkIsCurrencyUzs(String fromCcy, String toCcy) {
        log.info("Checking currency, from, to: {}, {}", fromCcy, toCcy);
        if (UZS_CCY.equals(fromCcy) && fromCcy.equals(toCcy)) {
            return true;
        }
        log.error("Currency is incorrect, from, to {}, {}", fromCcy, toCcy);
        return false;
    }

    @Override
    public boolean validateCheckPan(CardDto cardDto) {
        log.info("Checking card : {}", cardDto);
        if (Objects.isNull(cardDto) || Objects.isNull(cardDto.getRealPan())
                || cardDto.getRealPan().isBlank()) {
            log.error("Card real pan is empty");
            return false;
        }

        if (!Objects.equals(CardStatus.ACTIVE.name(), cardDto.getStatus())) {
            log.error("Card status is not ACTIVE");
            return false;
        }

        return true;
    }

    @Override
    public boolean validateSenderCardAmount(CardDto cardDto, Long amount) {
        log.info("Checking sender card amount : {}", cardDto);
        Long commissionAmount = calculateService.calculateCommissionAmount(COMMISSION_FEE, amount);
        Long transactionAmount = amount + commissionAmount;
        return cardDto.getBalance() >= transactionAmount;
    }

    @Override
    public boolean validateExternalPaymentTransactionResponse(ExternalPaymentTransactionDto externalPaymentTransactionDto) {
        log.info("Checking external payment transaction response: {}", externalPaymentTransactionDto);
        if (Objects.isNull(externalPaymentTransactionDto) || Objects.isNull(externalPaymentTransactionDto.getTransactionStatus())) {
            log.error("External payment transaction is empty");
            return false;
        }

        if (Boolean.FALSE.equals(externalPaymentTransactionDto.isSuccess())
                || !Objects.equals(TransactionStatus.SUCCESS, externalPaymentTransactionDto.getTransactionStatus())) {
            log.error("External payment transaction is not success with status {}", externalPaymentTransactionDto.getTransactionStatus().name());
            return false;
        }

        return true;
    }
}
