package com.company.controller;

import com.company.dto.CardDto;
import com.company.dto.ResponseDto;
import com.company.exp.CustomException;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardNumberForm;
import com.company.form.cards.CardStatusForm;
import com.company.service.CardService;
import com.company.util.CurrentUserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController extends DefaultController {

    private final CardService service;

    private CardDto check(String number) {
        return service.check(number);
    }

    private CardDto checkByUser(String number, String profileId) {
        CardDto card = service.checkByUser(number, profileId);
        if (card == null) {
            throw new CustomException("Card not found");
        }
        return card;
    }

    @GetMapping("/{cardId}")
    public ResponseDto<CardDto> findById(@PathVariable("cardId") String cardId) {
        return new ResponseDto<>(service.getByIdAndProfile(CurrentUserUtil.currentUser().getId(), cardId));
    }

    @GetMapping("/number")
    public ResponseDto<CardDto> findById(@RequestBody CardNumberForm form) {
        return new ResponseDto<>(checkByUser(form.getNumber(), CurrentUserUtil.currentUser().getId()));
    }

    @GetMapping("/get-all")
    public ResponseDto<List<CardDto>> findAllByUser(Authentication auth) {
        return new ResponseDto<>(service.findAll(CurrentUserUtil.currentUser().getId()));
    }

    @PostMapping()
    public ResponseDto<CardDto> createCard(@Valid @RequestBody CardForm form) {
        // Check card by number
        CardDto card = check(form.getNumber());
        if (card != null) {
            throw new CustomException("Such a digital card already exists.");
        }
        form.setProfileId(CurrentUserUtil.currentUser().getId());
        form.setProfilePhone(CurrentUserUtil.currentUser().getUsername());

        return new ResponseDto<>(service.createCard(form));
    }

    @PutMapping("/change-status")
    public ResponseDto<String> changeStatus(@Valid @RequestBody CardStatusForm form) {
        form.setProfileId(CurrentUserUtil.currentUser().getId());

        // Check card
        checkByUser(form.getNumber(), form.getProfileId());

        service.changeStatus(form);
        return new ResponseDto<>("Status changed");
    }

}
