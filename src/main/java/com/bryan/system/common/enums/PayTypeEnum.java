package com.bryan.system.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * PayTypeEnum 支付方式枚举
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum implements IEnum<Integer> {
    ALI_PAY(1, "支付宝"),
    WECHAT_PAY(2, "微信"),
    BALANCE(3, "余额");

    private final Integer code;
    private final String desc;

    public static PayTypeEnum of(Integer code) {
        for (PayTypeEnum e : values()) {
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
