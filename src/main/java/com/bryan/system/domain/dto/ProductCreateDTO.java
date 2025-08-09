package com.bryan.system.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * ProductCreateDTO
 *
 * @author Bryan Long
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
