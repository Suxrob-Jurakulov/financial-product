package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto<T> {

    private Integer count = 0;
    private Integer page = 0;
    private Integer limit = 0;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sort;
    private List<T> items;

    public PagingDto(Integer count, Integer page, Integer limit) {
        this.count = count;
        this.page = page;
        this.limit = limit;
        this.items = new ArrayList<>();
    }
}
