package com.softtech.graduationproject.prd.controller;

import com.softtech.graduationproject.gen.dto.RestResponse;
import com.softtech.graduationproject.prd.dto.PrdProductTypeDto;
import com.softtech.graduationproject.prd.dto.PrdProductTypeSaveRequestDto;
import com.softtech.graduationproject.prd.service.PrdProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-types")
@RequiredArgsConstructor
public class PrdProductTypeController {

    private final PrdProductTypeService prdProductTypeService;

    @GetMapping
    public ResponseEntity getPrdProductTypeDetails() {
        List<Object[]> prdProductTypeDetails = prdProductTypeService.getPrdProductTypeDetails();
        return ResponseEntity.ok(RestResponse.of(prdProductTypeDetails));
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateVat(@PathVariable Long id, @RequestParam Long vatRate) {
        PrdProductTypeDto prdProductTypeDto = prdProductTypeService.update(id, vatRate);
        return ResponseEntity.ok(RestResponse.of(prdProductTypeDto));
    }

    @PostMapping("/save-initial-data")
    public ResponseEntity saveInitialData() {
        List<PrdProductTypeDto> prdProductTypeDtoList = prdProductTypeService.saveInitialData();
        return ResponseEntity.ok(RestResponse.of(prdProductTypeDtoList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody PrdProductTypeSaveRequestDto prdProductTypeSaveRequestDto) {
        PrdProductTypeDto prdProductTypeDto = prdProductTypeService.save(prdProductTypeSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(prdProductTypeDto));
    }
}
