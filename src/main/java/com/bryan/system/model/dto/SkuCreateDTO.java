package com.bryan.system.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * SkuCreateDTO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
public class SkuCreateDTO {
    @NotNull
    private Long productId;

    @NotBlank
    private String skuCode;

    @NotBlank
    private String skuName;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    private String specifications;
}
