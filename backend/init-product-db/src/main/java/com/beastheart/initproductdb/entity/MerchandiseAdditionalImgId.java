package com.beastheart.initproductdb.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * MerchandiseAdditionalImg에서 복합키를 사용하기 위한 클래스
 */
@EqualsAndHashCode
public class MerchandiseAdditionalImgId implements Serializable {
    private Long merchandise;
    private String kind;
}
