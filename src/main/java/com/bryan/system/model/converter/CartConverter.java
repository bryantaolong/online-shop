package com.bryan.system.model.converter;

import com.bryan.system.model.dto.AddCartItemDTO;
import com.bryan.system.model.entity.cart.CartItem;
import com.bryan.system.model.vo.CartItemVO;

/**
 * CartConverter
 *
 * @author Bryan Long
 */
public class CartConverter {

    public static CartItem from(AddCartItemDTO dto, Long userId) {
        return CartItem.builder()
                .userId(userId)
                .skuId(dto.getSkuId())
                .quantity(dto.getQuantity())
                .build();
    }

    public static CartItemVO toVO(CartItem item) {
        return CartItemVO.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .productId(item.getProductId())
                .skuId(item.getSkuId())
                .productName(item.getProductName())
                .skuName(item.getSkuName())
                .productImage(item.getProductImage())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .specifications(item.getSpecifications())
                .createTime(item.getCreateTime())
                .build();
    }
}
