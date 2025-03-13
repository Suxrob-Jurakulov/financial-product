package com.company.domain.cards;

import com.company.domain.Profile;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Card {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, length = 16)
    private String number;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column
    private String name;

    @Column
    private Long balance;

    @Column
    private Integer password;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "profile_id", nullable = false)
    private String profileId;

    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Column
    private Boolean deleted;

    @Column
    private LocalDateTime createdDate;
}
