package com.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    @JsonIgnore
    private String password;
    private LocalDateTime createdDate;
    private List<ProfileModuleDto> modules;

}