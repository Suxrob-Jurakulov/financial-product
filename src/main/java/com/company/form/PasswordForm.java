package com.company.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {

    @NotBlank(message = "Phone cannot be empty")
    private String password;

    // Other element
    private String id;
}
