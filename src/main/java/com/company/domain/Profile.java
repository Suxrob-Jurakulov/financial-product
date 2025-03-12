package com.company.domain;

import com.company.dto.ProfileModuleDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sukhrob
 */

@Data
@Entity
public class Profile {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<ProfileModuleDto> modules;

    @Column
    private Long balance;

    @Column
    private LocalDateTime createdDate;

    @Column
    private Boolean deleted;

}
