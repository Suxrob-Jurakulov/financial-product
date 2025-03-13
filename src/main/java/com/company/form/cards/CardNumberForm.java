package com.company.form.cards;

import com.company.form.DefaultForm;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CardNumberForm extends DefaultForm<String> {

    @Digits(integer = 16, fraction = 0, message = "Must be a 16-digit number only")
    private String number;

}
