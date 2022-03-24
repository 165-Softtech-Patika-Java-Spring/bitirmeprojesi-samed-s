package com.softtech.graduationproject.prd.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class PrdProductTypeDetails {

    private final String type;
    private final Long vatRate;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final BigDecimal averagePrice;
    private final Long productCount;
}
