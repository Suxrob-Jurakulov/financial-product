package com.company.domain.cards;

import com.company.domain.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
@Setter
@Entity
public class Card {

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

    @Column(nullable = false, unique = true)
    private String realPan;

    @Column(nullable = false)
    private YearMonth expiryDate;

    @Column(name = "profile_id", nullable = false)
    private String profileId;

    @Column
    private String bin;

    @Column
    private String cardIssuingBank;

    @Column
    private Integer currencyCode;

    @Column
    private String phone;

    @Column
    private Boolean sms;

    @Column
    private Boolean deleted;

    @Column
    private LocalDateTime createdDate;

}
