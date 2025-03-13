package com.company.repository;

import com.company.domain.cards.Card;
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

    @Transactional
    @Modifying
    @Query("update Card set status = :status where realPan = :number and profileId = :profileId")
    void changeStatus(@Param("status") String status, @Param("number") String number, @Param("profileId") String profileId);

    List<Card> findAllByProfileIdAndDeletedIsFalse(String profileId);
}
