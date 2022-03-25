package com.softtech.graduationproject.prd.service;

import com.softtech.graduationproject.gen.exceptions.GenBusinessException;
import com.softtech.graduationproject.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.prd.converter.PrdProductMapper;
import com.softtech.graduationproject.prd.dto.PrdProductDto;
import com.softtech.graduationproject.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.prd.dto.PrdProductUpdateRequestDto;
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
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrdProductService {

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

    public PrdProductDto save(PrdProductSaveRequestDto prdProductSaveRequestDto) {

        BigDecimal price = prdProductSaveRequestDto.getPrice();
        controlIsPriceNegative(price);

        String name = prdProductSaveRequestDto.getName();
        Long prdProductTypeId = prdProductSaveRequestDto.getPrdProductTypeId();

        controlIsProductTypePriceNameNull(prdProductTypeId, price, name);

        BigDecimal priceWithVat = getPriceWithVatUsingPrdProductType(prdProductTypeId, price);

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductSaveRequestDto);
        PrdProduct existsProduct = prdProductEntityService.findByName(name);
        if(existsProduct != null){
            throw new GenBusinessException(PrdErrorMessage.PRODUCT_NAME_CANNOT_BE_USED);
        }

        PrdProductDto prdProductDto = setPriceWithVatAndSavePrdProduct(prdProduct, priceWithVat);

        return prdProductDto;
    }

    public PrdProductDto update(PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        Long productId = prdProductUpdateRequestDto.getId();
        isProductExists(productId);

        BigDecimal price = prdProductUpdateRequestDto.getPrice();
        controlIsPriceNegative(price);

        Long prdProductTypeId = prdProductUpdateRequestDto.getPrdProductTypeId();
        String name = prdProductUpdateRequestDto.getName();
        controlIsProductTypePriceNameNull(prdProductTypeId, price, name);

        BigDecimal priceWithVat = getPriceWithVatUsingPrdProductType(prdProductTypeId, price);

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductUpdateRequestDto);
        PrdProductDto prdProductDto = setPriceWithVatAndSavePrdProduct(prdProduct, priceWithVat);

        return prdProductDto;
    }

    public void delete(Long id) {
        PrdProduct prdProduct = prdProductEntityService.findByIdWithControl(id);
        prdProductEntityService.delete(prdProduct);
    }

    public PrdProductDto updatePrice(Long id, BigDecimal price) {
        controlIsPriceNegative(price);
        PrdProduct prdProduct = prdProductEntityService.findByIdWithControl(id);

        Long prdProductTypeId = prdProduct.getPrdProductTypeId();
        String name = prdProduct.getName();

        controlIsProductTypePriceNameNull(prdProductTypeId, price, name);

        BigDecimal priceWithVat = getPriceWithVatUsingPrdProductType(prdProductTypeId, price);

        prdProduct.setPrice(price);
        PrdProductDto prdProductDto = setPriceWithVatAndSavePrdProduct(prdProduct, priceWithVat);

        return prdProductDto;
    }

    public List<PrdProductDto> findAll() {
        List<PrdProduct> prdProductList = prdProductEntityService.findAll();
        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);
        return prdProductDtoList;
    }

    public List<PrdProductDto> findAllByProductType(String productType) {
        PrdProductType prdProductType = prdProductTypeEntityService.findByType(productType);
        Long prdProductTypeId = prdProductType.getId();

        List<PrdProduct> prdProductList = prdProductEntityService.findAllByPrdProductTypeId(prdProductTypeId);
        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;
    }

    public List<PrdProductDto> findAllByPriceWithVatBetween(BigDecimal minPriceWithVat, BigDecimal maxPriceWithVat) {
        controlIsPriceNegative(minPriceWithVat);
        controlIsPriceNegative(maxPriceWithVat);
        List<PrdProduct> prdProductList = prdProductEntityService.findAllByPriceWithVatBetween(minPriceWithVat, maxPriceWithVat);
        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);
        return prdProductDtoList;
    }

    private void isProductExists(Long id) {
        boolean isProductExists = prdProductEntityService.existsById(id);
        if(!isProductExists){
            throw new ItemNotFoundException(PrdErrorMessage.PRODUCT_NOT_FOUND);
        }
    }

    private BigDecimal getPriceWithVatUsingPrdProductType(Long prdProductTypeId, BigDecimal price) {
        PrdProductType prdProductType = prdProductTypeEntityService.findByIdWithControl(prdProductTypeId);
        Long vatRate = prdProductType.getVatRate();
        BigDecimal priceWithVat = prdProductEntityService.getPriceWithVat(price, vatRate);

        return priceWithVat;
    }

    private PrdProductDto setPriceWithVatAndSavePrdProduct(PrdProduct prdProduct, BigDecimal priceWithVat) {
        prdProduct.setPriceWithVat(priceWithVat);
        prdProduct = prdProductEntityService.save(prdProduct);
        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }

    private void controlIsPriceNegative(BigDecimal price) {
        if(price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenBusinessException(PrdErrorMessage.PRICE_CANNOT_BE_NEGATIVE);
        }
    }

    private void controlIsProductTypePriceNameNull(Long prdProductTypeId, BigDecimal price, String name) {
        if(!StringUtils.hasText(String.valueOf(prdProductTypeId)))
            throw new GenBusinessException(PrdErrorMessage.PRODUCT_TYPE_ID_CANNOT_BE_NULL);
        if(!StringUtils.hasText(String.valueOf(price)))
            throw new GenBusinessException(PrdErrorMessage.PRICE_CANNOT_BE_NULL);
        if(!StringUtils.hasText(name))
            throw new GenBusinessException(PrdErrorMessage.NAME_CANNOT_BE_NULL);
    }
}
