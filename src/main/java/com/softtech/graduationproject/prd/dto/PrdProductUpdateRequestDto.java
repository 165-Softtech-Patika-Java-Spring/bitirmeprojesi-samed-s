package com.softtech.graduationproject.prd.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrdProductUpdateRequestDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long prdProductTypeId;
}
