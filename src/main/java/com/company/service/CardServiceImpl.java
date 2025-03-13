package com.company.service;

import com.company.domain.cards.Card;
import com.company.domain.cards.CardStatus;
import com.company.dto.CardDto;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardPasswordForm;
import com.company.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;


   /* @Override
    public CardDto check(String number) {
        Optional<Card> cardOpl = repository.findByNumberAndDeletedIsFalse(number);

        return cardOpl.map(this::map).orElse(null);
    }

    @Override
    public CardDto checkByUser(CardPasswordForm form) {
        Optional<Card> cardOpl = repository.findByNumberAndProfileIdAndDeletedIsFalse(form.getNumber(), form.getProfileId());
        return cardOpl.map(this::map).orElse(null);
    }

    @Override
    public CardDto createCard(CardForm form) {
        Card card = new Card();

        card.setId(UUID.randomUUID().toString());
        card.setNumber(form.getNumber());
        card.setExpiryDate(LocalDate.parse(form.getExpiryDate()));
        card.setName(form.getName());
        card.setBalance(0L);
        card.setPassword(1111);
        card.setStatus(CardStatus.ACTIVE);
        card.setProfileId(form.getProfileId());
        card.setDeleted(false);
        card.setCreatedDate(LocalDateTime.now());

        return map(repository.save(card));
    }

    @Override
    public void changePassword(CardPasswordForm form) {
        repository.changePassword(form.getNumber(), form.getProfileId(), form.getPassword());
    }


    // MAPPER
    private CardDto map(Card card) {
        CardDto dto = new CardDto();

        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setExpiryDate(card.getExpiryDate());
        dto.setName(card.getName());
        dto.setStatus(card.getStatus().name());
        dto.setBalance(card.getBalance());
        dto.setProfileId(card.getProfileId());
        dto.setCreatedDate(card.getCreatedDate());

        return dto;
    }*/
}
