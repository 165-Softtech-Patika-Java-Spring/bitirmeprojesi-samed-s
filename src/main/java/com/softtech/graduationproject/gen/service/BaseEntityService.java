package com.softtech.graduationproject.gen.service;

import com.softtech.graduationproject.gen.entity.BaseAdditionalFields;
import com.softtech.graduationproject.gen.entity.BaseEntity;
import com.softtech.graduationproject.gen.enums.GenErrorMessage;
import com.softtech.graduationproject.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.sec.service.AuthenticationService;
import com.softtech.graduationproject.usr.entity.UsrUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private final D dao;
    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public List<E> findAll() {
        return dao.findAll();
    }

    public Optional<E> findById(Long id) {
        return dao.findById(id);
    }

    public E save(E entity) {
        setAdditionalFields(entity);
        return dao.save(entity);
    }

    private void setAdditionalFields(E entity) {
        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        Long currentUserId = getCurrentUserId();

        if(baseAdditionalFields == null) {
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if(entity.getId() == null) {
            baseAdditionalFields.setCreatedDate(new Date());
            baseAdditionalFields.setCreatedBy(currentUserId);
        }

        baseAdditionalFields.setUpdatedDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentUserId);
    }

    public void delete(E entity) {
        dao.delete(entity);
    }

    public E findByIdWithControl(Long id) {
        Optional<E> entityOptional = findById(id);

        E entity;
        if(entityOptional.isPresent()) {
            entity = entityOptional.get();
        }
        else {
            throw new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);
        }
        return entity;
    }

    public boolean existsById(Long id) {
        return dao.existsById(id);
    }

    public D getDao() {
        return dao;
    }

    public Long getCurrentUserId() {
        Long currentUserId = authenticationService.getCurrentUserId();
        return currentUserId;
    }

    public UsrUser getCurrentUser() {
        UsrUser currentUser = authenticationService.getCurrentUser();
        return currentUser;
    }
}
