package com.company.controller;

import com.company.dto.JwtDto;
import com.company.dto.ProfileDto;
import com.company.dto.TokenDto;
import com.company.exp.BadRequestException;
import com.company.form.ProfileForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProfileDto> register(@Valid @RequestBody ProfileForm form) {

        // Check profile in Database
        ProfileDto profile = check(form.getPhone());
        if (profile != null) {
            throw new BadRequestException("This phone is busy!");
        }

        // Encode password
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        return ResponseEntity.ok().body(authService.add(form));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody ProfileForm form) {

        // Check profile in Database
        ProfileDto profile = check(form.getPhone());
        if (profile == null) {
            throw new BadRequestException("User not found!");
        }

        // Check password
        if (!passwordEncoder.matches(form.getPassword(), profile.getPassword())) {
            throw new BadRequestException("Wrong password!");
        }

        form.setProfile(profile);

        // Create and save token
        TokenDto dto = tokensService.add(form.getProfile());

        return ResponseEntity.ok(new JwtDto(dto.getUid(), dto.getAccessToken(), dto.getRefreshToken()));
    }

}