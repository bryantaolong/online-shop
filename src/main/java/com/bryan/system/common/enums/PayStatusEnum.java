package com.bryan.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * PayStatusEnum 支付状态枚举
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {
    WAITING(0, "待支付"),
    SUCCESS(1, "支付成功"),
    FAIL(2, "支付失败"),
    REFUND(3, "已退款");

    private final Integer code;
    private final String desc;

    public static PayStatusEnum of(Integer code) {
        for (PayStatusEnum e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
