package com.company.form.cards;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardForm {

    @Digits(integer = 16, fraction = 0, message = "Must be a 16-digit number only")
    private String number;

    @NotBlank(message = "Card date cannot be empty!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Expiry date must be in the future")
    private String expiryDate;

    private String name;

    // Other elements
    private String profileId;
}
