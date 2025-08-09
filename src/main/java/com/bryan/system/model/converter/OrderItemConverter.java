package com.bryan.system.model.converter;

import com.bryan.system.model.entity.cart.CartItem;
import com.bryan.system.model.entity.order.OrderItem;
import com.bryan.system.model.vo.OrderItemVO;

import java.math.BigDecimal;

/**
 * OrderItemConverter
 *
 * @author Bryan Long
 */
public class OrderItemConverter {

    public static OrderItem from(CartItem cart, Long orderId, String orderNo) {
        return OrderItem.builder()
                .orderId(orderId)
                .orderNo(orderNo)
                .productId(cart.getProductId())
                .skuId(cart.getSkuId())
                .productName(cart.getProductName())
                .skuName(cart.getSkuName())
                .productImage(cart.getProductImage())
                .productPrice(cart.getPrice())
                .quantity(cart.getQuantity())
                .totalPrice(cart.getPrice()
                        .multiply(BigDecimal.valueOf(cart.getQuantity())))
                .specifications(cart.getSpecifications())
                .build();
    }

    public static OrderItemVO toVO(OrderItem item) {
        return OrderItemVO.builder()
                .id(item.getId())
                .productName(item.getProductName())
                .skuName(item.getSkuName())
                .productImage(item.getProductImage())
                .productPrice(item.getProductPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}
