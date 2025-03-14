package com.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sukhrob
 */
@Getter
@AllArgsConstructor
public enum Errors {

    CUSTOM(1000),
    NOT_FOUND(1100);

    private final Integer value;
}
