package com.company.form.cards;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPasswordForm {

    @Digits(integer = 16, fraction = 0, message = "Must be a 16-digit number only")
    private String number;

    @Digits(integer = 4, fraction = 0, message = "Must be a 4-digit number only")
    private String password;

    // Other elements
    private String profileId;
}
