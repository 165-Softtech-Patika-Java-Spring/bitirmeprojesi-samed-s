package com.softtech.graduationproject.prd.controller;

import com.softtech.graduationproject.gen.dto.RestResponse;
import com.softtech.graduationproject.prd.dto.PrdProductDto;
import com.softtech.graduationproject.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.prd.service.PrdProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class PrdProductController {

    private final PrdProductService prdProductService;

    @PostMapping
    public ResponseEntity save(@RequestBody PrdProductSaveRequestDto prdProductSaveRequestDto) {
        PrdProductDto prdProductDto = prdProductService.save(prdProductSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody PrdProductUpdateRequestDto prdProductUpdateRequestDto) {
        PrdProductDto prdProductDto = prdProductService.update(prdProductUpdateRequestDto);
        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        prdProductService.delete(id);
        return ResponseEntity.ok(RestResponse.empty());
    }

    @PatchMapping("/{id}")
    public ResponseEntity updatePrice(@PathVariable Long id, @RequestParam BigDecimal price) {
        PrdProductDto prdProductDto = prdProductService.updatePrice(id, price);
        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<PrdProductDto> prdProductDtoList = prdProductService.findAll();
        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }

    @GetMapping("/{productType}")
    public ResponseEntity findAllByProductType(@PathVariable String productType) {
        List<PrdProductDto> prdProductDtoList = prdProductService.findAllByProductType(productType);
        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }

    @GetMapping("/price")
    public ResponseEntity findAllByPriceWithVatBetween(@RequestParam BigDecimal minPriceWithVat, @RequestParam BigDecimal maxPriceWithVat) {
        List<PrdProductDto> prdProductDtoList = prdProductService.findAllByPriceWithVatBetween(minPriceWithVat, maxPriceWithVat);
        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }
}
