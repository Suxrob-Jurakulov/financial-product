package com.company.service;

import com.company.dto.CardDto;
import com.company.dto.ExternalPaymentTransactionDto;

public interface ValidateService {

    boolean checkIsCurrencyUzs(String fromCcy, String toCcy);

    boolean validateCheckPan(CardDto cardDto);

    boolean validateSenderCardAmount(CardDto cardDto, Long amount);

    boolean validateExternalPaymentTransactionResponse(ExternalPaymentTransactionDto externalPaymentTransactionDto);
}
