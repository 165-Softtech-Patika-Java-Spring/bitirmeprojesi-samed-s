package com.softtech.graduationproject.prd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrdProductTypeSaveRequestDto {
    private String type;
    private Long vatRate;
}
