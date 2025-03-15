package com.company.service;

import com.company.domain.cards.Card;
import com.company.domain.cards.CardStatus;
import com.company.dto.CardDto;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardStatusForm;
import com.company.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.company.util.Constants.GENERAL_COMMISSION_CARD;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    @Override
    public CardDto check(String number) {
        Optional<Card> cardOpl = repository.findByRealPanAndDeletedIsFalse(number);

        return cardOpl.map(this::map).orElse(null);
    }

    @Override
    public CardDto checkByUser(String number, String profileId) {
        Optional<Card> cardOpl = repository.findByRealPanAndProfileIdAndDeletedIsFalse(number, profileId);
        return cardOpl.map(this::map).orElse(null);
    }

    @Override
    public List<CardDto> findAll(String profileId) {
        List<Card> cardList = repository.findAllByProfileIdAndDeletedIsFalse(profileId);
        if (!cardList.isEmpty()) {
            return cardList.stream().map(this::map).toList();
        }

        return List.of();
    }

    @Override
    public CardDto getByIdAndProfile(String id, String cardId) {
        Optional<Card> cardOpl = repository.findByIdAndProfileIdAndDeletedIsFalse(cardId, id);
        return cardOpl.map(this::map).orElse(null);
    }

    @Override
    public CardDto createCard(CardForm form) {
        Card card = new Card();

        card.setId(UUID.randomUUID().toString());
        card.setName(form.getName());
        card.setBalance(0L);
        card.setStatus(CardStatus.ACTIVE);
        card.setMaskedPan(form.getNumber().substring(0, 6) + "******" + form.getNumber().substring(12));
        card.setRealPan(form.getNumber());
        card.setExpiryDate(form.getExpiryDate());
        card.setProfileId(form.getProfileId());
        card.setBin(form.getNumber().substring(0, 8));
        card.setCardIssuingBank("Universal Bank");
        card.setCurrencyCode(860);
        card.setPhone(form.getProfilePhone());
        card.setSms(true);
        card.setDeleted(false);
        card.setCreatedDate(LocalDateTime.now());

        return map(repository.save(card));
    }

    @Override
    public void changeStatus(CardStatusForm form) {
        repository.changeStatus(CardStatus.valueOf(form.getStatus()), form.getNumber(), form.getProfileId());
    }

    @Override
    public void debitCard(String senderPan, Long amount) {
        Optional<Card> cardOpl = repository.findByRealPanAndDeletedIsFalse(senderPan);
        if (cardOpl.isPresent()) {
            Card card = cardOpl.get();
            long newBalance = card.getBalance() - amount;
            updateCardBalance(card, newBalance);
        }
    }

    @Override
    public void creditCard(String recipientPan, Long amount) {
        Optional<Card> cardOpl = repository.findByRealPanAndDeletedIsFalse(recipientPan);
        if (cardOpl.isPresent()) {
            Card card = cardOpl.get();
            long newBalance = card.getBalance() + amount;
            updateCardBalance(card, newBalance);
        }
    }

    @Override
    public void creditCommissionCard(Long amount) {
        Optional<Card> cardOpl = repository.findByRealPanAndDeletedIsFalse(GENERAL_COMMISSION_CARD);
        if (cardOpl.isPresent()) {
            Card card = cardOpl.get();
            long newBalance = card.getBalance() + amount;
            updateCardBalance(card, newBalance);
        }
    }

    private void updateCardBalance(Card card, long newBalance) {
        log.info("Updated balance {} to {}", card.getBalance(), newBalance);
        repository.updateBalance(card.getId(), newBalance);
    }


    // MAPPER
    private CardDto map(Card card) {
        CardDto dto = new CardDto();

        dto.setId(card.getId());
        dto.setName(card.getName());
        dto.setBalance(card.getBalance());
        dto.setStatus(card.getStatus().name());
        dto.setMaskedPan(card.getMaskedPan());
        dto.setRealPan(card.getRealPan());
        dto.setExpiryDate(card.getExpiryDate());
        dto.setProfileId(card.getProfileId());
        dto.setBin(card.getBin());
        dto.setCardIssuingBank(card.getCardIssuingBank());
        dto.setCurrencyCode(card.getCurrencyCode());
        dto.setPhone(card.getPhone());
        dto.setSms(card.getSms());
        dto.setDeleted(card.getDeleted());
        dto.setCreatedDate(card.getCreatedDate());

        return dto;
    }
}
