package com.company.controller;

import com.company.config.CustomUserDetails;
import com.company.dto.CardDto;
import com.company.exp.BadRequestException;
import com.company.form.cards.CardForm;
import com.company.form.cards.CardNumberForm;
import com.company.form.cards.CardStatusForm;
import com.company.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            throw new BadRequestException("Card not found");
        }
        return card;
    }

    @GetMapping("/cardId")
    public ResponseEntity<CardDto> findById(@RequestParam("cardId") String cardId, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        return ResponseEntity.ok(service.getByIdAndProfile(userDetails.getId(), cardId));
    }

    @GetMapping("/number")
    public ResponseEntity<CardDto> findById(@RequestBody CardNumberForm form, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        return ResponseEntity.ok(checkByUser(form.getNumber(), userDetails.getId()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CardDto>> findAllByUser(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        return ResponseEntity.ok(service.findAll(userDetails.getId()));
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
        form.setProfilePhone(userDetails.getUsername());

        return ResponseEntity.ok(service.createCard(form));
    }

    @PutMapping("/change-status")
    public ResponseEntity<String> changePassword(@Valid @RequestBody CardStatusForm form, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        form.setProfileId(userDetails.getId());

        // Check card
        checkByUser(form.getNumber(), form.getProfileId());

        service.changeStatus(form);

        return ResponseEntity.ok().body("Status changed");
    }

}
