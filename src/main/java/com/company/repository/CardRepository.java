package com.company.repository;

import com.company.domain.cards.Card;
import com.company.domain.cards.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String>, JpaSpecificationExecutor<Card> {

    Optional<Card> findByRealPanAndDeletedIsFalse(String number);

    Optional<Card> findByRealPanAndProfileIdAndDeletedIsFalse(String number, String profileId);

    Optional<Card> findByIdAndProfileIdAndDeletedIsFalse(String cardId, String id);

    List<Card> findAllByProfileIdAndDeletedIsFalse(String profileId);

    @Transactional
    @Modifying
    @Query("update Card set status = :status where realPan = :realPan and profileId = :profileId")
    void changeStatus(@Param("status") CardStatus status, @Param("realPan") String realPan, @Param("profileId") String profileId);

    @Transactional
    @Modifying
    @Query("update Card c set c.balance = :newBalance where c.id = :cardId")
    void updateBalance(@Param("cardId") String cardId, @Param("newBalance") Long newBalance);

}
