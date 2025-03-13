package com.company.repository;

import com.company.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sukhrob
 */
@Repository
public interface ProfileRepository extends JpaRepository<UserProfile, String> {

    Optional<UserProfile> findByPhoneAndDeletedIsFalse(String phone);

    Optional<UserProfile> findByIdAndDeletedIsFalse(String id);
}