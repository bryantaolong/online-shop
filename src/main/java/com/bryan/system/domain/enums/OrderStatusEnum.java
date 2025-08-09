package com.bryan.system.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OrderStatusEnum 订单状态枚举
 *
 * @author Bryan Long
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    PENDING_PAYMENT(0, "待付款"),
    PAID(1, "已付款"),
    SHIPPED(2, "已发货"),
    RECEIVED(3, "已收货"),
    CLOSED(4, "已关闭"),
    REFUNDING(5, "退款中"),
    REFUNDED(6, "已退款");

    private final Integer code;
    private final String desc;

    public static OrderStatusEnum of(Integer code) {
        for (OrderStatusEnum e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
