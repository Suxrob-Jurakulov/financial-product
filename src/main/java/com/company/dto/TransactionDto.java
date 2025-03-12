package com.company.dto;

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
    private String name;
    private String email;
    private Long balance;
    private LocalDateTime createdAt;
    private Boolean deleted;
}
