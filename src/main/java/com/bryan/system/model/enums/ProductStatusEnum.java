package com.bryan.system.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ProductStatus 商品状态枚举
 *
 * @author Bryan Long
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {
    OFF_SHELF(0, "下架"),
    ON_SHELF(1, "上架");

    private final Integer code;
    private final String desc;

    public static ProductStatusEnum of(Integer code) {
        for (ProductStatusEnum e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
