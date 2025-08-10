package com.bryan.system.domain.converter;

import com.bryan.system.domain.dto.ProductCreateDTO;
import com.bryan.system.domain.entity.product.Product;
import com.bryan.system.domain.vo.ProductVO;

/**
 * ProductConverter
 *
 * @author Bryan Long
 */
public class ProductConverter {

    public static Product toEntity(ProductCreateDTO dto) {
        return Product.builder()
                .name(dto.getName())
                .categoryId(dto.getCategoryId())
                .brandId(dto.getBrandId())
                .description(dto.getDescription())
                .mainImage(dto.getMainImage())
                .build();
    }

    public static ProductVO toVO(Product p) {
        return ProductVO.builder()
                .id(p.getId())
                .name(p.getName())
                .categoryId(p.getCategoryId())
                .brandId(p.getBrandId())
                .description(p.getDescription())
                .mainImage(p.getMainImage())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
