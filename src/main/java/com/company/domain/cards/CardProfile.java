package com.company.domain.cards;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class CardProfile {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column
    private Long balance;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(nullable = false)
    private String maskedPan;

    @Column(nullable = false)
    private String realPan;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column
    private String token;

    @Column
    private String cardIssuingBank;

    @Column
    private Integer currencyCode;
}
