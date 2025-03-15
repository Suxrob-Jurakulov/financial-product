package com.company.dto;

import com.company.dto.profile.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.YearMonth;

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
    private YearMonth expiryDate;
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
