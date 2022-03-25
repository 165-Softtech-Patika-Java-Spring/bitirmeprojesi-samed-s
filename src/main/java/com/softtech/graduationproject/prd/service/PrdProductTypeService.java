package com.softtech.graduationproject.prd.service;

import com.softtech.graduationproject.gen.exceptions.GenBusinessException;
import com.softtech.graduationproject.prd.converter.PrdProductTypeMapper;
import com.softtech.graduationproject.prd.dto.PrdProductTypeDto;
import com.softtech.graduationproject.prd.dto.PrdProductTypeSaveRequestDto;
import com.softtech.graduationproject.prd.entity.PrdProduct;
import com.softtech.graduationproject.prd.entity.PrdProductType;
import com.softtech.graduationproject.prd.enums.PrdErrorMessage;
import com.softtech.graduationproject.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.prd.service.entityservice.PrdProductTypeEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrdProductTypeService {

    private PrdProductEntityService prdProductEntityService;

    @Autowired
    public void setPrdProductEntityService(@Lazy PrdProductEntityService prdProductEntityService) {
        this.prdProductEntityService = prdProductEntityService;
    }

    private PrdProductTypeEntityService prdProductTypeEntityService;

    @Autowired
    public void setPrdProductTypeEntityService(@Lazy PrdProductTypeEntityService prdProductTypeEntityService) {
        this.prdProductTypeEntityService = prdProductTypeEntityService;
    }

    public List<Object[]> getPrdProductTypeDetails() {
        return prdProductTypeEntityService.getPrdProductTypeDetails();
    }

    public PrdProductTypeDto update(Long id, Long vat) {
        PrdProductType prdProductType = prdProductTypeEntityService.findByIdWithControl(id);

        controlIsVatRateNegative(vat);

        prdProductType.setVatRate(vat);

        prdProductType = prdProductTypeEntityService.save(prdProductType);

        Long prdProductTypeId = prdProductType.getId();
        List<PrdProduct> prdProductList = prdProductEntityService.findAllByPrdProductTypeId(prdProductTypeId);

        for (PrdProduct prdProduct : prdProductList) {
            BigDecimal price = prdProduct.getPrice();
            BigDecimal priceWithVat = prdProductEntityService.getPriceWithVat(price, vat);

            prdProduct.setPriceWithVat(priceWithVat);

            prdProduct = prdProductEntityService.save(prdProduct);
        }

        PrdProductTypeDto prdProductTypeDto = PrdProductTypeMapper.INSTANCE.convertToPrdProductTypeDto(prdProductType);
        return prdProductTypeDto;
    }

    public List<PrdProductTypeDto> saveInitialData() {
        List<PrdProductTypeDto> prdProductTypeDtoList = new ArrayList<>();
        List<PrdProductTypeSaveRequestDto> prdProductSaveRequestDtoList = getInitialPrdProductTypeSaveRequestDtoList();
        List<PrdProductType> prdProductTypeList = PrdProductTypeMapper.INSTANCE.convertToPrdProductTypeList(prdProductSaveRequestDtoList);

        for(PrdProductType prdProductTypeListItem: prdProductTypeList) {
            Long vatRate = prdProductTypeListItem.getVatRate();
            controlIsVatRateNegative(vatRate);

            String type = prdProductTypeListItem.getType();
            PrdProductType prdProductType = prdProductTypeEntityService.findByType(type);
            if(prdProductType != null){
                continue;
            }

            prdProductTypeListItem = prdProductTypeEntityService.save(prdProductTypeListItem);
            prdProductTypeDtoList.add(PrdProductTypeMapper.INSTANCE.convertToPrdProductTypeDto(prdProductTypeListItem));
        }

        return prdProductTypeDtoList;
    }

    public PrdProductTypeDto save(PrdProductTypeSaveRequestDto prdProductTypeSaveRequestDto) {
        PrdProductType prdProductType = PrdProductTypeMapper.INSTANCE.convertToPrdProductType(prdProductTypeSaveRequestDto);

        Long vatRate = prdProductType.getVatRate();
        controlIsVatRateNegative(vatRate);

        String type = prdProductType.getType();
        PrdProductType existsProductType = prdProductTypeEntityService.findByType(type);
        if(existsProductType != null){
            throw new GenBusinessException(PrdErrorMessage.TYPE_NAME_CANNOT_BE_USED);
        }

        prdProductType = prdProductTypeEntityService.save(prdProductType);
        PrdProductTypeDto prdProductTypeDto = PrdProductTypeMapper.INSTANCE.convertToPrdProductTypeDto(prdProductType);

        return prdProductTypeDto;
    }

    private List<PrdProductTypeSaveRequestDto> getInitialPrdProductTypeSaveRequestDtoList() {
        List<PrdProductTypeSaveRequestDto> prdProductSaveRequestDtoList = new ArrayList<>();
        prdProductSaveRequestDtoList.add(new PrdProductTypeSaveRequestDto("FOOD", 1L));
        prdProductSaveRequestDtoList.add(new PrdProductTypeSaveRequestDto("STATIONERY", 8L));
        prdProductSaveRequestDtoList.add(new PrdProductTypeSaveRequestDto("CLOTHING", 8L));
        prdProductSaveRequestDtoList.add(new PrdProductTypeSaveRequestDto("TECHNOLOGY", 18L));
        prdProductSaveRequestDtoList.add(new PrdProductTypeSaveRequestDto("CLEANING", 18L));
        prdProductSaveRequestDtoList.add(new PrdProductTypeSaveRequestDto("OTHERS", 18L));

        return prdProductSaveRequestDtoList;
    }

    private void controlIsVatRateNegative(Long vatRate) {
        if(vatRate < 0) {
            throw new GenBusinessException(PrdErrorMessage.VAT_RATE_CANNOT_BE_NEGATIVE);
        }
    }
}
