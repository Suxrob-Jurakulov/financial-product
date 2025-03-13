package com.company.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;

@Data
@AllArgsConstructor
@AutoConfigurationPackage
public class AuthBasicDto {

    private String phone;
    private String password;
}
