package com.company.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Sukhrob
 */

@Data
@Entity
public class Transaction {

    @Id
    private String id;

    @Column(name = "profile_id")
    private String userId;

    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Column
    private Long amount;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Boolean deleted;

}
