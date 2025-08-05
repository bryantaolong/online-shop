package com.bryan.system.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * ProductCreateDTO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
public class ProductCreateDTO {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;

    @Size(max = 500)
    private String description;

    @Size(max = 255)
    private String mainImage;
}
