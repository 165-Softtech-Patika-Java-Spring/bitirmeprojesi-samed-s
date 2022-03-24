package com.softtech.graduationproject.prd.converter;

import com.softtech.graduationproject.prd.dto.PrdProductDto;
import com.softtech.graduationproject.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.prd.entity.PrdProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrdProductMapper {
    PrdProductMapper INSTANCE = Mappers.getMapper(PrdProductMapper.class);
    PrdProduct convertToPrdProduct(PrdProductSaveRequestDto prdProductSaveRequestDto);
    PrdProduct convertToPrdProduct(PrdProductUpdateRequestDto prdProductUpdateRequestDto);
    PrdProductDto convertToPrdProductDto(PrdProduct prdProduct);
    List<PrdProductDto> convertToPrdProductDtoList(List<PrdProduct> prdProductList);
}
