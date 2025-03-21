package com.company.repository;

import com.company.domain.transactions.Transaction;
import com.company.domain.transactions.TransactionStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionSpecification {

    public Specification<Transaction> hasSenderProfileId(String senderProfileId) {
        return (root, query, cb) -> cb.equal(root.get("senderProfileId"), senderProfileId);
    }

    public Specification<Transaction> hasRecipientProfileId(String recipientProfileId) {
        return (root, query, cb) -> cb.equal(root.get("recipientProfileId"), recipientProfileId);
    }

    public Specification<Transaction> hasStatus(TransactionStatus status) {
        return (root, query, cb) -> cb.equal(root.get("transactionStatus"), status);
    }

    public Specification<Transaction> hasSenderCard(String senderCard) {
        return (root, query, cb) -> cb.equal(root.get("senderCard"), senderCard);
    }

    public Specification<Transaction> hasRecipientCard(String recipientCard) {
        return (root, query, cb) -> cb.equal(root.get("recipientCard"), recipientCard);
    }

    public Specification<Transaction> hasDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null) {
                return cb.between(root.get("createdDate"), startDate, endDate);
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("createdDate"), startDate);
            } else if (endDate != null) {
                return cb.lessThanOrEqualTo(root.get("createdDate"), endDate);
            }
            return cb.conjunction();
        };
    }
}
