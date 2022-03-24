package com.softtech.graduationproject.prd.dao;

import com.softtech.graduationproject.prd.entity.PrdProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrdProductTypeDao extends JpaRepository<PrdProductType, Long> {

    PrdProductType findByType(String type);

    @Query("SELECT " +
            "prdProductType.type," +
            "prdProductType.vatRate," +
            "min(prdProduct.priceWithVat)," +
            "max(prdProduct.priceWithVat)," +
            "avg(prdProduct.priceWithVat)," +
            "count(prdProductType.type)" +
            " FROM com.softtech.graduationproject.prd.entity.PrdProduct prdProduct " +
            "LEFT JOIN com.softtech.graduationproject.prd.entity.PrdProductType prdProductType ON prdProduct.prdProductTypeId = prdProductType.id " +
            "GROUP BY prdProductType.type, prdProductType.vatRate")
    List<Object[]> getPrdProductTypeDetails();


}
