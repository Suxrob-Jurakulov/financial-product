package com.company.controller;

import com.company.config.CustomUserDetails;
import com.company.dto.CardDto;
import com.company.exp.BadRequestException;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardPasswordForm;
import com.company.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController extends DefaultController {

    private final CardService service;

    /*private CardDto check(String number) {
        return service.check(number);
    }

    private CardDto checkByUser(CardPasswordForm form) {
        CardDto card = service.checkByUser(form);
        if (card == null) {
            throw new BadRequestException("Card not found");
        }
        return card;
    }

    @PostMapping("/create")
    public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardForm form, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Check card by number
        CardDto card = check(form.getNumber());
        if (card != null) {
            throw new BadRequestException("Such a digital card already exists.");
        }

        form.setProfileId(userDetails.getId());

        return ResponseEntity.ok(service.createCard(form));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody CardPasswordForm form, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        form.setProfileId(userDetails.getId());

        // Check card
        checkByUser(form);

        service.changePassword(form);

        return ResponseEntity.ok().body("Password changed");
    }*/

    /*@GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getUserCards(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserCards(userId));
    }*/

}
