package com.company.controller;

import com.company.dto.ResponseDto;
import com.company.dto.profile.ProfileDto;
import com.company.form.PasswordForm;
import com.company.form.ProfileEditForm;
import com.company.service.ProfileService;
import com.company.util.CurrentUserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController extends DefaultController {

    private final ProfileService service;

    @GetMapping
    public ResponseDto<ProfileDto> getProfile() {
        return new ResponseDto<>(service.getProfile(CurrentUserUtil.currentUser().getId()));
    }

    @PutMapping
    public ResponseDto<ProfileDto> edit(@Valid @RequestBody ProfileEditForm form) {
        form.setProfileId(CurrentUserUtil.currentUser().getId());

        return new ResponseDto<>(service.edit(form));
    }

    @PutMapping("/change-password")
    public ResponseDto<ProfileDto> changePassword(@Valid @RequestBody PasswordForm form) {
        form.setProfileId(CurrentUserUtil.currentUser().getId());
        // Encode password
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        return new ResponseDto<>(service.changePassword(form));
    }
}
