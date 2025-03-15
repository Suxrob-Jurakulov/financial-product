package com.company.config;


import com.company.domain.transactions.Transaction;
import com.company.domain.transactions.TransactionStatus;
import com.company.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.company.config.CardDatabaseInitializer.PROFILE1_CARD_NUMBER;
import static com.company.config.CardDatabaseInitializer.PROFILE2_CARD_NUMBER;
import static com.company.config.ProfileDatabaseInitializer.PROFILE_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionDatabaseInitializer {

    public static final String TRANSACTION_SUCCESS_ID = "66fcd807-6026-486a-a212-47ad51139933";
    public static final String TRANSACTION_FAILED_ID = "66fcd807-6026-486a-a212-47ad51139944";
    public static final String TRANSACTION_TIMEOUT_ID = "66fcd807-6026-486a-a212-47ad51139955";

    private final TransactionRepository repository;

    @PostConstruct
    public void init() {

        // TRANSACTION Success
        if (repository.findById(TRANSACTION_SUCCESS_ID).isEmpty()) {
            Transaction transaction = new Transaction();

            transaction.setId(TRANSACTION_SUCCESS_ID);
            transaction.setSenderCard(PROFILE1_CARD_NUMBER);
            transaction.setRecipientCard(PROFILE2_CARD_NUMBER);
            transaction.setProfileId(PROFILE_ID);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transaction.setSuccess(true);
            transaction.setRequestAmount(1000L);
            transaction.setCommissionAmount(20L);
            transaction.setCommissionFee(2.0);
            transaction.setTransactionAmount(1020L);
            transaction.setCreatedDate(LocalDateTime.now());

            repository.save(transaction);
        }

        // TRANSACTION Failed
        if (repository.findById(TRANSACTION_FAILED_ID).isEmpty()) {
            Transaction transaction = new Transaction();

            transaction.setId(TRANSACTION_FAILED_ID);
            transaction.setSenderCard(PROFILE1_CARD_NUMBER);
            transaction.setRecipientCard(PROFILE2_CARD_NUMBER);
            transaction.setProfileId(PROFILE_ID);
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setSuccess(false);
            transaction.setRequestAmount(1000L);
            transaction.setCommissionAmount(20L);
            transaction.setCommissionFee(2.0);
            transaction.setTransactionAmount(1020L);
            transaction.setCreatedDate(LocalDateTime.now());

            repository.save(transaction);
        }

        // TRANSACTION Timeout
        if (repository.findById(TRANSACTION_TIMEOUT_ID).isEmpty()) {
            Transaction transaction = new Transaction();

            transaction.setId(TRANSACTION_TIMEOUT_ID);
            transaction.setSenderCard(PROFILE1_CARD_NUMBER);
            transaction.setRecipientCard(PROFILE2_CARD_NUMBER);
            transaction.setProfileId(PROFILE_ID);
            transaction.setTransactionStatus(TransactionStatus.TIMEOUT);
            transaction.setSuccess(false);
            transaction.setRequestAmount(1000L);
            transaction.setCommissionAmount(20L);
            transaction.setCommissionFee(2.0);
            transaction.setTransactionAmount(1020L);
            transaction.setCreatedDate(LocalDateTime.now());

            repository.save(transaction);
        }

    }

}
