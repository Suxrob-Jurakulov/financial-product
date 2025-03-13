package com.company.dto;

import com.company.dto.profile.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private String id;
    private String name;
    private Long balance;
    private String status;
    private String maskedPan;
    private String realPan;
    private LocalDate expiryDate;
    private String profileId;
    private ProfileDto profile;
    private String bin;
    private String cardIssuingBank;
    private Integer currencyCode;
    private String phone;
    private Boolean sms;
    private Boolean deleted;
    private LocalDateTime createdDate;

}
