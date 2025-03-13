package com.company.controller;

import com.company.service.AuthService;
import com.company.service.TokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultController {

    @Autowired
    AuthService authService;

    @Autowired
    TokensService tokensService;

    @Autowired
    PasswordEncoder passwordEncoder;

}
