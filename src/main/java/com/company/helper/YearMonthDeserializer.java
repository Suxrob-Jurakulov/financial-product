package com.company.helper;

import com.company.exp.CustomException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearMonthDeserializer extends JsonDeserializer<YearMonth> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");

    @Override
    public YearMonth deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText().trim();
        try {
            return YearMonth.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CustomException("Invalid date format: '" + date + "'. Expected format: yyyy/MM (e.g., 2025/07)");
        }
    }
}
