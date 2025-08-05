package com.bryan.system.model.request.product;

import com.bryan.system.common.enums.ProductStatusEnum;
import com.bryan.system.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ProductPageQuery
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPageQuery extends PageRequest {
    private String name;          // 商品名称（模糊）
    private Long categoryId;      // 分类 ID
    private Long brandId;         // 品牌 ID
    private ProductStatusEnum status; // 状态：ON_SHELF / OFF_SHELF
}
