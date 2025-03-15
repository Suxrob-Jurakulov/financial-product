package com.company.dto;

import com.company.dto.profile.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Sukhrob
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private String id;
    private String senderCard;
    private String recipientCard;
    private String profileId;
    private ProfileDto profile;
    private String externalTransactionId;
    private String transactionStatus;
    private Boolean success;
    private Long requestAmount;
    private Long commissionAmount;
    private Double commissionFee;
    private Long transactionAmount;
    private LocalDateTime createdDate;

}
