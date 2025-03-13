package com.company.service;

import com.company.dto.CardDto;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardPasswordForm;

public interface CardService {

    CardDto check(String number);

    CardDto checkByUser(CardPasswordForm form);

    CardDto createCard(CardForm form);

    void changePassword(CardPasswordForm form);
}
