package com.bryan.system.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * AddCartItemDTO
 *
 * @author Bryan Long
 */
@Data
public class AddCartItemDTO {
    @NotNull
    private Long skuId;

    @Positive
    private Integer quantity;
}
