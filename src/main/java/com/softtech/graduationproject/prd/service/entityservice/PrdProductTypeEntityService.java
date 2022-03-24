package com.softtech.graduationproject.prd.service.entityservice;

import com.softtech.graduationproject.gen.service.BaseEntityService;
import com.softtech.graduationproject.prd.dao.PrdProductTypeDao;
import com.softtech.graduationproject.prd.entity.PrdProductType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrdProductTypeEntityService extends BaseEntityService<PrdProductType, PrdProductTypeDao> {

    public PrdProductTypeEntityService(PrdProductTypeDao dao) {
        super(dao);
    }

    public PrdProductType findByType(String type) {
        return getDao().findByType(type);
    }

    public List<Object[]> getPrdProductTypeDetails() {
        return getDao().getPrdProductTypeDetails();
    }
}
