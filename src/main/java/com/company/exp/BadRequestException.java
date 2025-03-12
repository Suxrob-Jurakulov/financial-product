package com.company.exp;

/**
 * @author Sukhrob
 */

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
