package com.company.form;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TransactionParamsForm extends DefaultForm<String> {

    @Pattern(regexp = "credit|debit", message = "Invalid type value. Allowed types: credit, debit")
    private String type;

    @NotBlank(message = "Page cannot be empty")
    @Min(value = 1, message = "The value must be at least 1.")
    private Integer page;

    @NotBlank(message = "Limit cannot be empty")
    @Min(value = 1, message = "The value must be at least 1.")
    @Max(value = 60, message = "The value must not exceed 60")
    private Integer limit;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;


    // Validate startDate and endDate
    @AssertTrue(message = "Start date cannot be after end date")
    public boolean isValidDateRange() {
        return startDate == null || endDate == null || !startDate.isAfter(endDate);
    }
}
