package com.bryan.system.model.request.product;

import com.bryan.system.model.enums.ProductStatusEnum;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * ProductPageQuery
 *
 * @author Bryan Long
 */
@Getter
public class ProductSearchRequest {

    @Size(max = 128, message = "商品名称不能超过128个字符")
    private String name;              // 模糊匹配

    @PositiveOrZero(message = "分类ID必须为非负整数")
    private Long categoryId;

    @PositiveOrZero(message = "品牌ID必须为非负整数")
    private Long brandId;

    private ProductStatusEnum status; // 状态：ON_SHELF / OFF_SHELF / null=不限
}
