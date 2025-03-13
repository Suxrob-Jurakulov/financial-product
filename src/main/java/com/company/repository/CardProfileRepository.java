package com.company.repository;

import com.company.domain.cards.CardProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CardProfileRepository extends JpaRepository<CardProfile, String>, JpaSpecificationExecutor<CardProfile> {


}
