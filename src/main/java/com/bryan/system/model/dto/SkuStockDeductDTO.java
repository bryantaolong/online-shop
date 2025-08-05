package com.bryan.system.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * SkuStockDeductDTO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
public class SkuStockDeductDTO {
    @NotNull
    private Long skuId;

    @Positive
    private Integer quantity;
}
