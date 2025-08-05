package com.bryan.system.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * SkuVO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@Builder
public class SkuVO {
    private Long id;
    private Long productId;
    private String skuCode;
    private String skuName;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer stock;
    private String specifications;
}
