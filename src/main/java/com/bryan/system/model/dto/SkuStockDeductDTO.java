package com.bryan.system.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * SkuStockDeductDTO
 *
 * @author Bryan Long
 */
@Data
public class SkuStockDeductDTO {
    @NotNull
    private Long skuId;

    @Positive
    private Integer quantity;
}
