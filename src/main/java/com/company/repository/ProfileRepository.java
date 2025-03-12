package com.company.repository;

import com.company.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sukhrob
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByUsernameAndDeletedIsFalse(String username);

    Optional<Profile> findByIdAndDeletedIsFalse(String id);
}