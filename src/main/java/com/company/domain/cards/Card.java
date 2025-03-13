package com.company.domain.cards;

import com.company.domain.UserProfile;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Card {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(name = "user_profile_id", nullable = false)
    private String userProfileId;

    @JoinColumn(name = "user_profile_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @Column(name = "card_profile_id", nullable = false)
    private String cardProfileId;

    @JoinColumn(name = "card_profile_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private CardProfile cardProfile;

    @Column
    private String phone;

    @Column
    private Boolean sms;

    @Column
    private String bin;

    @Column
    private Boolean deleted;

}
