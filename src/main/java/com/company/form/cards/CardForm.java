package com.company.form.cards;

import com.company.form.DefaultForm;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CardForm extends DefaultForm {

    @NotBlank(message = "Card number cannot be empty! ")
    @Digits(integer = 16, fraction = 0, message = "Must be a 16-digit number only")
    private String number;

    @NotBlank(message = "Card date cannot be empty!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //todo '28/03, 2028/07'
    @Future(message = "Expiry date must be in the future")
    private String expiryDate;

    @NotBlank(message = "Card name cannot be empty!")
    private String name;

    // Other elements
    private String profilePhone;
}
