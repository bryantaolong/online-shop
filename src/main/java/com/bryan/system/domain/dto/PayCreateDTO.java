package com.bryan.system.domain.dto;

import com.bryan.system.domain.enums.PayTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * PayCreateDTO
 *
 * @author Bryan Long
 */
@Data
public class PayCreateDTO {
    @NotBlank
    private String orderNo;   // 订单号

    @NotNull
    private PayTypeEnum type; // 支付渠道：ALI_PAY / WECHAT_PAY / BALANCE

    private BigDecimal amount; // 允许部分支付时传入，默认传 null 表示全额
}
