package com.bryan.system.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * OrderVO
 *
 * @author Bryan Long
 */
@Data
@Builder
public class OrderVO {
    private Long id;
    private String orderNo;
    private BigDecimal totalAmount;
    private BigDecimal paymentAmount;
    private String statusDesc;
    private List<OrderItemVO> items;
}
