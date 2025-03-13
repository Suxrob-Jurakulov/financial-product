package com.company.form.cards;

import com.company.form.DefaultForm;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CardStatusForm extends DefaultForm {

    @Digits(integer = 16, fraction = 0, message = "Must be a 16-digit number only")
    private String number;

    @NotNull(message = "Status cannot be null")
    @Pattern(regexp = "ACTIVE|BLOCKED|EXPIRED|STOLEN", message = "Invalid status value. Allowed values: ACTIVE, BLOCKED, EXPIRED, STOLEN")
    private String status;

}
