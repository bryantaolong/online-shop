package com.bryan.system.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderItemVO
 *
 * @author Bryan Long
 */
@Data
@Builder
public class OrderItemVO {
    private Long id;
    private String productName;
    private String skuName;
    private String productImage;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
}
