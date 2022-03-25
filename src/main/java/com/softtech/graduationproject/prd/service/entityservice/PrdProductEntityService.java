package com.softtech.graduationproject.prd.service.entityservice;

import com.softtech.graduationproject.gen.service.BaseEntityService;
import com.softtech.graduationproject.prd.dao.PrdProductDao;
import com.softtech.graduationproject.prd.entity.PrdProduct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PrdProductEntityService extends BaseEntityService<PrdProduct, PrdProductDao> {

    public PrdProductEntityService(PrdProductDao dao) {
        super(dao);
    }

    public List<PrdProduct> findAllByPrdProductTypeId(Long prdProductTypeId) {
        return getDao().findAllByPrdProductTypeId(prdProductTypeId);
    }

    public List<PrdProduct> findAllByPriceWithVatBetween(BigDecimal minPriceWithVat, BigDecimal maxPriceWithVat) {
        return getDao().findAllByPriceWithVatBetween(minPriceWithVat, maxPriceWithVat);
    }

    public BigDecimal getPriceWithVat(BigDecimal price, Long vat) {
        BigDecimal vatValue = price.multiply(BigDecimal.valueOf(vat)).divide(BigDecimal.valueOf(100));
        BigDecimal priceWithVat =  price.add(vatValue);
        return priceWithVat;
    }

    public PrdProduct findByName(String name) {
        return getDao().findByName(name);
    }
}
