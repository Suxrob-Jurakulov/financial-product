package com.company.controller;

import com.company.dto.ResponseDto;
import com.company.dto.auth.JwtDto;
import com.company.dto.auth.TokenDto;
import com.company.dto.profile.ProfileDto;
import com.company.form.TokenForm;
import com.company.helper.JwtHelper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController extends DefaultController {

    private final JwtHelper jwtHelper;

    @PostMapping("/refresh")
    public ResponseDto<JwtDto> refresh(@RequestBody TokenForm form) {

        // Parse token
        String tokenId;
        try {
            tokenId = jwtHelper.extractId(form.getRefreshToken());
        } catch (JwtException e) {
            throw new BadCredentialsException("Token is not valid!");
        }

        // Check token to expired
        if (jwtHelper.isTokenExpired(form.getRefreshToken())) {
            throw new BadCredentialsException("Token is expired!");
        }

        // Check token to type
        if (!jwtHelper.extractType(form.getRefreshToken()).equals("refresh")) {
            throw new BadCredentialsException("Token type is not refresh!");
        }

        // Check has token in database
        TokenDto refreshToken = tokensService.getRefreshById(tokenId);
        if (refreshToken == null) {
            throw new BadCredentialsException("Token is deleted!");
        }

        // Check has user in database
        ProfileDto profile = authService.getById(jwtHelper.extractUserId(form.getRefreshToken()));
        if (profile == null) {
            throw new BadCredentialsException("User not found!");
        }

        // Set auth to form
        TokenDto dto = tokensService.add(profile);

        return new ResponseDto<>(new JwtDto(dto.getUid(), dto.getAccessToken(), dto.getRefreshToken()));
    }

}
