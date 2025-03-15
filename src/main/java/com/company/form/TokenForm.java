package com.company.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenForm {

    @NotBlank(message = "Refresh token cannot be null or empty")
    private String refreshToken;
}
