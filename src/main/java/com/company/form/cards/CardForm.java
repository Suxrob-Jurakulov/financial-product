package com.company.form.cards;

import com.company.form.DefaultForm;
import com.company.helper.YearMonthDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CardForm extends DefaultForm {

    @NotBlank(message = "Card number cannot be empty! ")
    @Pattern(regexp = "\\d{16}", message = "Must be a 16-digit number only")
    private String number;

    @NotNull(message = "Expiry date is required!")
    @JsonDeserialize(using = YearMonthDeserializer.class)
    private YearMonth expiryDate;

    @NotBlank(message = "Card name cannot be empty!")
    private String name;

    // Other elements
    private String profilePhone;

    @AssertTrue(message = "Expiry date must be in the future")
    public boolean isExpiryDateValid() {
        return expiryDate != null && expiryDate.isAfter(YearMonth.now());
    }
}
