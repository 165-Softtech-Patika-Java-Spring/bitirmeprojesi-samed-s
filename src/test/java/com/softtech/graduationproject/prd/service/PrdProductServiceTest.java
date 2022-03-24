package com.softtech.graduationproject.prd.service;

import com.softtech.graduationproject.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.prd.dto.PrdProductDto;
import com.softtech.graduationproject.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.prd.entity.PrdProduct;
import com.softtech.graduationproject.prd.entity.PrdProductType;
import com.softtech.graduationproject.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.prd.service.entityservice.PrdProductTypeEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrdProductServiceTest {

    @InjectMocks
    PrdProductService prdProductService;

    @Mock
    private PrdProductEntityService prdProductEntityService;

    @Mock
    private PrdProductTypeEntityService prdProductTypeEntityService;

    @Test
    void shouldSave() {
        PrdProductSaveRequestDto prdProductSaveRequestDto = mock(PrdProductSaveRequestDto.class);
        when(prdProductSaveRequestDto.getPrice()).thenReturn(new BigDecimal(100));
        when(prdProductSaveRequestDto.getPrdProductTypeId()).thenReturn(1L);

        PrdProductType prdProductType = mock(PrdProductType.class);
        when(prdProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prdProductType);
        when(prdProductType.getVatRate()).thenReturn(18L);
        
        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getId()).thenReturn(1L);

        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto result = prdProductService.save(prdProductSaveRequestDto);

        assertEquals(1L, result.getId());

    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> prdProductService.save(null));
    }

    @Test
    void shouldUpdate() {
        Long id = 1L;

        PrdProductUpdateRequestDto prdProductUpdateRequestDto = mock(PrdProductUpdateRequestDto.class);
        when(prdProductUpdateRequestDto.getPrice()).thenReturn(new BigDecimal(100));
        when(prdProductUpdateRequestDto.getPrdProductTypeId()).thenReturn(1L);

        PrdProductType prdProductType = mock(PrdProductType.class);
        when(prdProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prdProductType);
        when(prdProductType.getVatRate()).thenReturn(18L);

        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getId()).thenReturn(id);


        boolean isExists = true;

        when(prdProductEntityService.existsById(anyLong())).thenReturn(isExists);

        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto prdProductDto = prdProductService.update(prdProductUpdateRequestDto);
        assertEquals(id, prdProductDto.getId());

    }

    @Test
    void shouldDelete() {
        PrdProduct prdProduct = mock(PrdProduct.class);

        when(prdProductEntityService.findByIdWithControl(anyLong())).thenReturn(prdProduct);
        prdProductService.delete(anyLong());

        verify(prdProductEntityService).findByIdWithControl(anyLong());
        verify(prdProductEntityService).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExists() {

        when(prdProductEntityService.findByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> prdProductService.delete(anyLong()));
        verify(prdProductEntityService).findByIdWithControl(anyLong());
    }

    @Test
    void shouldUpdatePrice() {
        Long id = 1L;
        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getPrdProductTypeId()).thenReturn(1L);
        when(prdProduct.getId()).thenReturn(id);
        when(prdProductEntityService.findByIdWithControl(anyLong())).thenReturn(prdProduct);

        PrdProductType prdProductType = mock(PrdProductType.class);
        when(prdProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prdProductType);
        when(prdProductType.getVatRate()).thenReturn(18L);


        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto prdProductDto = prdProductService.updatePrice(id, new BigDecimal(100));
        assertEquals(id, prdProductDto.getId());
    }

    @Test
    void shouldFindAll() {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        PrdProductDto prdProductDto = mock(PrdProductDto.class);
        List<PrdProductDto> prdProductDtoList = new ArrayList<>();
        prdProductDtoList.add(prdProductDto);


        when(prdProductEntityService.findAll()).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findAll();

        assertEquals(prdProductDtoList.size(), prdProductList.size());
        assertEquals(result.size(), prdProductDtoList.size());
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenPrdProductListIsEmpty() {

        when(prdProductEntityService.findAll()).thenReturn(new ArrayList<>());

        List<PrdProductDto> result = prdProductService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindAllWhenPrdProductListIsNull() {

        when(prdProductEntityService.findAll()).thenReturn(null);

        List<PrdProductDto> result = prdProductService.findAll();

        assertEquals(null, result);
    }

    @Test
    void shouldFindAllByProductType() {
        PrdProductType prdProductType = mock(PrdProductType.class);
        when(prdProductTypeEntityService.findByType(anyString())).thenReturn(prdProductType);
        when(prdProductType.getId()).thenReturn(1L);

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        PrdProductDto prdProductDto = mock(PrdProductDto.class);
        List<PrdProductDto> prdProductDtoList = new ArrayList<>();
        prdProductDtoList.add(prdProductDto);


        when(prdProductEntityService.findAllByPrdProductTypeId(anyLong())).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findAllByProductType(anyString());

        assertEquals(prdProductDtoList.size(), prdProductList.size());
        assertEquals(result.size(), prdProductDtoList.size());
        assertEquals(1, result.size());


        assertEquals(1L, prdProductType.getId());
    }

    @Test
    void shouldFindAllByPriceWithVatBetween() {
        BigDecimal minPrice = new BigDecimal(100);
        BigDecimal maxPrice = new BigDecimal(200);
        BigDecimal price = new BigDecimal(150);

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        PrdProductDto prdProductDto = mock(PrdProductDto.class);
        List<PrdProductDto> prdProductDtoList = new ArrayList<>();
        prdProductDtoList.add(prdProductDto);


        when(prdProduct.getPriceWithVat()).thenReturn(price);
        when(prdProductEntityService.findAllByPriceWithVatBetween(minPrice, maxPrice)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findAllByPriceWithVatBetween(minPrice, maxPrice);

        assertEquals(prdProductDtoList.size(), prdProductList.size());
        assertEquals(result.size(), prdProductDtoList.size());
        assertEquals(1, result.size());
    }
}