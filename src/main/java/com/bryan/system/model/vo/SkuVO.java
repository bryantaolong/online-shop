package com.bryan.system.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * SkuVO
 *
 * @author Bryan Long
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
