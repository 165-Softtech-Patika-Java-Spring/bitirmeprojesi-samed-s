package com.softtech.graduationproject.prd.entity;

import com.softtech.graduationproject.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRD_PRODUCT")
@Getter
@Setter
public class PrdProduct extends BaseEntity {

    @Id
    @SequenceGenerator(name = "PrdProduct", sequenceName = "PRD_PRODUCT_ID_SEQ")
    @GeneratedValue(generator = "PrdProduct")
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "PRICE", precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "PRICE_WITH_VAT", precision = 19, scale = 2, nullable = false)
    private BigDecimal priceWithVat;

    @Column(name = "ID_PRD_PRODUCT_TYPE")
    private Long prdProductTypeId;
}
