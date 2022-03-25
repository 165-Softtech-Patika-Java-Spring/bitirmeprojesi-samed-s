package com.softtech.graduationproject.prd.dao;

import com.softtech.graduationproject.prd.entity.PrdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PrdProductDao extends JpaRepository<PrdProduct, Long> {

    List<PrdProduct> findAllByPrdProductTypeId(Long prdProductTypeId);
    List<PrdProduct> findAllByPriceWithVatBetween(BigDecimal minPriceWithVat, BigDecimal maxPriceWithVat);
    PrdProduct findByName(String name);
}
