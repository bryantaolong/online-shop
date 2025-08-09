package com.bryan.system.domain.vo;

import com.bryan.system.domain.enums.ProductStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ProductVO
 *
 * @author Bryan Long
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
