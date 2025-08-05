package com.bryan.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RefundResult
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundResult {
    private boolean success;
    private String refundNo;        // 渠道退款单号
    private BigDecimal refundAmount;
    private LocalDateTime refundTime;
    private String errorMsg;        // 失败时返回
}
