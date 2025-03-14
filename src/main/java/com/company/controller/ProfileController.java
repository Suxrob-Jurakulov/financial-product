package com.company.controller;

import com.company.config.CustomUserDetails;
import com.company.dto.ResponseDto;
import com.company.dto.profile.ProfileDto;
import com.company.form.PasswordForm;
import com.company.form.ProfileEditForm;
import com.company.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController extends DefaultController {

    private final ProfileService service;

    @GetMapping
    public ResponseDto<ProfileDto> getProfile(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new ResponseDto<>(service.getProfile(userDetails.getId()));
    }

    @PutMapping
    public ResponseDto<ProfileDto> edit(@Valid @RequestBody ProfileEditForm form, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        form.setProfileId(userDetails.getId());

        return new ResponseDto<>(service.edit(form));
    }

    @PutMapping("/change-password")
    public ResponseDto<ProfileDto> changePassword(@Valid @RequestBody PasswordForm form, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        form.setProfileId(userDetails.getId());

        // Encode password
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        return new ResponseDto<>(service.changePassword(form));
    }
}
