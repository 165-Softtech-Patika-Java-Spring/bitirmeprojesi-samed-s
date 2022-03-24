package com.softtech.graduationproject.prd.converter;

import com.softtech.graduationproject.prd.dto.PrdProductTypeDto;
import com.softtech.graduationproject.prd.dto.PrdProductTypeSaveRequestDto;
import com.softtech.graduationproject.prd.entity.PrdProductType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrdProductTypeMapper {
    PrdProductTypeMapper INSTANCE = Mappers.getMapper(PrdProductTypeMapper.class);
    PrdProductTypeDto convertToPrdProductTypeDto(PrdProductType prdProductType);
    List<PrdProductType> convertToPrdProductTypeList(List<PrdProductTypeSaveRequestDto> prdProductTypeSaveRequestDtoList);
    PrdProductType convertToPrdProductType(PrdProductTypeSaveRequestDto prdProductTypeSaveRequestDto);
}
