package com.bryan.system.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * CreateOrderDTO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
public class CreateOrderDTO {
    @NotNull
    private Long userId;

    // 其余字段（地址、优惠券等）按需扩展
}
