package com.bryan.system.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentVO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@Builder
public class PaymentVO {
    private Long id;
    private String orderNo;
    private String paymentType;
    private String transactionId;
    private BigDecimal paymentAmount;
    private String statusDesc;
    private LocalDateTime paymentTime;
}
