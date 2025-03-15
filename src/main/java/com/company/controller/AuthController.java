package com.company.controller;

import com.company.dto.ResponseDto;
import com.company.dto.auth.JwtDto;
import com.company.dto.auth.TokenDto;
import com.company.dto.profile.ProfileDto;
import com.company.exp.CustomException;
import com.company.form.ProfileForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends DefaultController {

    private ProfileDto check(String username) {
        return authService.check(username);
    }

    @PostMapping("/register")
    public ResponseDto<ProfileDto> register(@Valid @RequestBody ProfileForm form) {

        // Check profile in Database
        ProfileDto profile = check(form.getPhone());
        if (profile != null) {
            throw new CustomException("This phone is busy!");
        }

        // Encode password
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        return new ResponseDto<>(authService.add(form));
    }

    @PostMapping("/login")
    public ResponseDto<JwtDto> login(@Valid @RequestBody ProfileForm form) {

        // Check profile in Database
        ProfileDto profile = check(form.getPhone());
        if (profile == null) {
            throw new CustomException("User not found!");
        }

        // Check password
        if (!passwordEncoder.matches(form.getPassword(), profile.getPassword())) {
            throw new CustomException("Wrong password!");
        }

        // Create and save token
        TokenDto dto = tokensService.add(profile);

        return new ResponseDto<>(new JwtDto(dto.getUid(), dto.getAccessToken(), dto.getRefreshToken()));
    }

}