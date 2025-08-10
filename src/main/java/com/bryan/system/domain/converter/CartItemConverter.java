package com.bryan.system.domain.converter;

import com.bryan.system.domain.entity.cart.CartItem;
import com.bryan.system.domain.vo.CartItemVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CartItemConverter
 *
 * @author Bryan Long
 */
public class CartItemConverter {

    public static CartItem toEntity(CartItemVO vo) {
        return CartItem.builder()
                .id(vo.getId())
                .userId(vo.getUserId())
                .productId(vo.getProductId())
                .skuId(vo.getSkuId())
                .productName(vo.getProductName())
                .skuName(vo.getSkuName())
                .productImage(vo.getProductImage())
                .price(vo.getPrice())
                .quantity(vo.getQuantity())
                .specifications(vo.getSpecifications())
                .createdAt(vo.getCreatedAt())
                .build();
    }

    public static List<CartItem> toEntityList(List<CartItemVO> vos) {
        return vos.stream()
                .map(CartItemConverter::toEntity)
                .collect(Collectors.toList());
    }

    /** entity → VO（单条） */
    public static CartItemVO toVO(CartItem entity) {
        return CartItemVO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .skuId(entity.getSkuId())
                .productName(entity.getProductName())
                .skuName(entity.getSkuName())
                .productImage(entity.getProductImage())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .specifications(entity.getSpecifications())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    /** entity → VO（批量） */
    public static List<CartItemVO> toVOList(List<CartItem> entities) {
        return entities.stream()
                .map(CartItemConverter::toVO)
                .collect(Collectors.toList());
    }
}
