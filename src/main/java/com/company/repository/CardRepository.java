package com.company.repository;

import com.company.domain.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String>, JpaSpecificationExecutor<Card> {


    Optional<Card> findByNumberAndDeletedIsFalse(String number);

    Optional<Card> findByNumberAndProfileIdAndDeletedIsFalse(String number, String profileId);

    @Modifying
    @Transactional
    @Query("update Card set password = :password where number = :number and profileId = :profileId and deleted = false")
    void changePassword(@Param("number") String number, @Param("profileId") String profileId, @Param("password") String password);
}
