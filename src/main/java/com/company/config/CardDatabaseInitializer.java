package com.company.config;


import com.company.domain.cards.Card;
import com.company.domain.cards.CardStatus;
import com.company.repository.CardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.UUID;

import static com.company.config.ProfileDatabaseInitializer.*;
import static com.company.util.Constants.GENERAL_COMMISSION_CARD;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardDatabaseInitializer {

    public static final YearMonth GENERAL_COMMISSION_CARD_EXPIRY_DATE = YearMonth.of(2030, 1);

    public static final String PROFILE1_CARD_NUMBER = "8600112233445577";
    public static final YearMonth PROFILE1_CARD_EXPIRY_DATE = YearMonth.of(2027, 1);

    public static final String PROFILE2_CARD_NUMBER = "8600112233445588";
    public static final YearMonth PROFILE2_CARD_EXPIRY_DATE = YearMonth.of(2026, 1);

    private final CardRepository repository;

    @PostConstruct
    public void init() {

        // ADMIN CARD
        if (repository.findByRealPanAndDeletedIsFalse(GENERAL_COMMISSION_CARD).isEmpty()) {
            Card card = new Card();

            card.setId(UUID.randomUUID().toString());
            card.setName("General Commission Card");
            card.setBalance(100000L);
            card.setStatus(CardStatus.ACTIVE);
            card.setMaskedPan("860011******5566");
            card.setRealPan(GENERAL_COMMISSION_CARD);
            card.setExpiryDate(GENERAL_COMMISSION_CARD_EXPIRY_DATE);
            card.setProfileId(ADMIN_PROFILE_ID);
            card.setBin(GENERAL_COMMISSION_CARD.substring(0, 8));
            card.setCardIssuingBank("General bank");
            card.setCurrencyCode(860);
            card.setPhone(ADMIN_PHONE);
            card.setSms(true);
            card.setDeleted(false);
            card.setCreatedDate(LocalDateTime.now());

            repository.save(card);
        }

        // USER1 CARD
        if (repository.findByRealPanAndDeletedIsFalse(PROFILE1_CARD_NUMBER).isEmpty()) {
            Card card = new Card();

            card.setId(UUID.randomUUID().toString());
            card.setName("Profile1 card");
            card.setBalance(100000L);
            card.setStatus(CardStatus.ACTIVE);
            card.setMaskedPan("860011******5577");
            card.setRealPan(PROFILE1_CARD_NUMBER);
            card.setExpiryDate(PROFILE1_CARD_EXPIRY_DATE);
            card.setProfileId(PROFILE_ID);
            card.setBin(PROFILE1_CARD_NUMBER.substring(0, 8));
            card.setCardIssuingBank("Kapital bank");
            card.setCurrencyCode(860);
            card.setPhone(PROFILE_PHONE);
            card.setSms(true);
            card.setDeleted(false);
            card.setCreatedDate(LocalDateTime.now());

            repository.save(card);
        }

        // USER2 CARD
        if (repository.findByRealPanAndDeletedIsFalse(PROFILE2_CARD_NUMBER).isEmpty()) {
            Card card = new Card();

            card.setId(UUID.randomUUID().toString());
            card.setName("Profile1 card");
            card.setBalance(100000L);
            card.setStatus(CardStatus.ACTIVE);
            card.setMaskedPan("860011******5588");
            card.setRealPan(PROFILE2_CARD_NUMBER);
            card.setExpiryDate(PROFILE2_CARD_EXPIRY_DATE);
            card.setProfileId(PROFILE2_ID);
            card.setBin(PROFILE2_CARD_NUMBER.substring(0, 8));
            card.setCardIssuingBank("Anor bank");
            card.setCurrencyCode(860);
            card.setPhone(PROFILE2_PHONE);
            card.setSms(true);
            card.setDeleted(false);
            card.setCreatedDate(LocalDateTime.now());

            repository.save(card);
        }

    }

}
