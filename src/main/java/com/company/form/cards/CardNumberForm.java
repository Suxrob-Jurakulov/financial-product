package com.company.form.cards;

import com.company.form.DefaultForm;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CardNumberForm extends DefaultForm<String> {

    @NotBlank(message = "Card number cannot be empty! ")
    @Pattern(regexp = "\\d{16}", message = "Must be a 16-digit number only")
    private String number;

}
