package com.company.service;

import com.company.dto.CardDto;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardStatusForm;

import java.util.List;

public interface CardService {

    CardDto check(String number);

    CardDto checkByUser(String number, String profileId);

    List<CardDto> findAll(String profileId);

    CardDto getByIdAndProfile(String id, String cardId);

    CardDto createCard(CardForm form);

    void changeStatus(CardStatusForm form);

    void debitCard(String senderPan, Long amount);

    void creditCard(String recipientPan, Long amount);

    void creditCommissionCard(Long amount);
}
