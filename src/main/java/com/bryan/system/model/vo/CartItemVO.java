package com.bryan.system.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CartItemVO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@Builder
public class CartItemVO {
    private Long id;
    private Long userId;
    private Long productId;
    private Long skuId;
    private String productName;
    private String skuName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private String specifications;
    private LocalDateTime createTime;
}
