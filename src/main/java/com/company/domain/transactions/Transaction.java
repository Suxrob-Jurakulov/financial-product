package com.company.domain.transactions;

import com.company.domain.Profile;
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
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String senderCard;

    @Column
    private String recipientCard;

    @Column(name = "user_profile_id")
    private String userProfileId;

    @JoinColumn(name = "user_profile_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Column
    private String externalTransactionId;

    @Column
    private String externalTransactionStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column
    private Boolean success;

    @Column
    private Long requestAmount;

    @Column
    private Long commissionAmount;

    @Column
    private Double commissionFee;

    @Column
    private Long transactionAmount;

    @Column
    private LocalDateTime createdDate;

}
