package com.company.controller;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.ProfileDto;
import com.company.form.PasswordForm;
import com.company.form.ProfileEditForm;
import com.company.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController extends DefaultController {

    private final ProfileService service;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok().body(service.getProfile(userDetails.getId()));
    }

    @PutMapping
    public ResponseEntity<ProfileDto> edit(@Valid @RequestBody ProfileEditForm form, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        form.setId(userDetails.getId());

        return ResponseEntity.ok().body(service.edit(form));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ProfileDto> changePassword(@Valid @RequestBody PasswordForm form, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        form.setId(userDetails.getId());

        // Encode password
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        return ResponseEntity.ok().body(service.changePassword(form));
    }
}
