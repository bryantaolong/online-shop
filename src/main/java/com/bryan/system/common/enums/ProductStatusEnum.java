package com.bryan.system.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ProductStatus 商品状态枚举
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum implements IEnum<Integer> {
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

    @Override
    public Integer getValue() {
        return code;
    }
}
