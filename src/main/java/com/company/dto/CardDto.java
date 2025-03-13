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
    private String number;
    private LocalDate expiryDate;
    private String name;
    private String status;
    private Long balance;
    private String profileId;
    private ProfileDto profile;
    private LocalDateTime createdDate;

}
