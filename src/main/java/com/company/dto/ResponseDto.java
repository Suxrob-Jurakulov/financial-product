package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Sukhrob
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponseDto(T data) {
        this.success = true;
        this.data = data;
    }

    public ResponseDto(Integer code, T data) {
        this.success = false;
        this.code = code;
        this.data = data;
        this.timestamp = new Date();
    }
}
