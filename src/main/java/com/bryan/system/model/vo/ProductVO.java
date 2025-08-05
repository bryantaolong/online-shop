package com.bryan.system.model.vo;

import com.bryan.system.common.enums.ProductStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ProductVO
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@Builder
public class ProductVO {
    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private String description;
    private String mainImage;
    private ProductStatusEnum status;
    private LocalDateTime createTime;
}
