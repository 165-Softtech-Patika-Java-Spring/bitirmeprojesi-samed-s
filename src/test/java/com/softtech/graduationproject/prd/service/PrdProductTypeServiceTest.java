package com.softtech.graduationproject.prd.service;

import com.softtech.graduationproject.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.prd.service.entityservice.PrdProductTypeEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrdProductTypeServiceTest {

    @Mock
    private PrdProductTypeEntityService prdProductTypeEntityService;

    @Mock
    PrdProductEntityService prdProductEntityService;

    @InjectMocks
    PrdProductTypeService prdProductTypeService;

    @Test
    void shouldUpdate() {
    }
}