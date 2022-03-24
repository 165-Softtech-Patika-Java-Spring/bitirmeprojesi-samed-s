package com.softtech.graduationproject.prd.entity;

import com.softtech.graduationproject.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PRD_PRODUCT_TYPE")
@Getter
@Setter
public class PrdProductType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "PrdProductType", sequenceName = "PRD_PRODUCT_TYPE_ID_SEQ")
    @GeneratedValue(generator = "PrdProductType")
    private Long id;

    @Column(name = "TYPE", length = 100, nullable = false, unique = true)
    private String type;

    @Column(name = "VAT_RATE", precision = 19, scale = 2, nullable = false)
    private Long vatRate;
}
