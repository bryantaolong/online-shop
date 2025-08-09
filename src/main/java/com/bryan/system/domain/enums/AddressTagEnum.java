package com.bryan.system.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AddressTagEnum 收货地址标签枚举
 *
 * @author Bryan Long
 */
@Getter
@AllArgsConstructor
public enum AddressTagEnum {
    HOME(1, "家"),
    COMPANY(2, "公司"),
    SCHOOL(3, "学校"),
    OTHER(4, "其他");

    private final Integer code;
    private final String desc;

    public static AddressTagEnum of(Integer code) {
        for (AddressTagEnum e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
