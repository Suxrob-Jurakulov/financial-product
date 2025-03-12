package com.company.repository;

import com.company.domain.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Sukhrob
 */
@Repository
public interface TokensRepository extends JpaRepository<Tokens, String> {

    Optional<Tokens> findByIdAndRefreshExpirationDateAfterAndDeletedIsFalse(String tokenId, Date date);
}
