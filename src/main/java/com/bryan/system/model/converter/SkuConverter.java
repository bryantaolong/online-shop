package com.bryan.system.model.converter;

import com.bryan.system.model.dto.SkuCreateDTO;
import com.bryan.system.model.entity.product.ProductSku;
import com.bryan.system.model.vo.SkuVO;

/**
 * SkuConverter
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
public class SkuConverter {

    public static ProductSku toEntity(SkuCreateDTO dto) {
        return ProductSku.builder()
                .productId(dto.getProductId())
                .skuCode(dto.getSkuCode())
                .skuName(dto.getSkuName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .specifications(dto.getSpecifications())
                .build();
    }

    public static SkuVO toVO(ProductSku sku) {
        return SkuVO.builder()
                .id(sku.getId())
                .productId(sku.getProductId())
                .skuCode(sku.getSkuCode())
                .skuName(sku.getSkuName())
                .price(sku.getPrice())
                .originalPrice(sku.getOriginalPrice())
                .stock(sku.getStock())
                .specifications(sku.getSpecifications())
                .build();
    }
}
