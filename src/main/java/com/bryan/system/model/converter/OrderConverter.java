package com.bryan.system.model.converter;

import com.bryan.system.common.enums.OrderStatusEnum;
import com.bryan.system.model.dto.CreateOrderDTO;
import com.bryan.system.model.entity.cart.CartItem;
import com.bryan.system.model.entity.order.Order;
import com.bryan.system.model.entity.order.OrderItem;
import com.bryan.system.model.vo.OrderItemVO;
import com.bryan.system.model.vo.OrderVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * OrderConverter
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
public class OrderConverter {

    public static Order toEntity(CreateOrderDTO dto, String orderNo) {
        return Order.builder()
                .orderNo(orderNo)
                .userId(dto.getUserId())
                .status(OrderStatusEnum.PENDING_PAYMENT)
                .build();
    }

    public static OrderItem toEntity(CartItem item, Long orderId, String orderNo) {
        return OrderItem.builder()
                .orderId(orderId)
                .orderNo(orderNo)
                .productId(item.getProductId())
                .skuId(item.getSkuId())
                .productName(item.getProductName())
                .skuName(item.getSkuName())
                .productImage(item.getProductImage())
                .productPrice(item.getPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .specifications(item.getSpecifications())
                .build();
    }

    /* 用于详情：Order + 明细列表 → OrderVO */
    public static OrderVO toVO(Order o, List<OrderItemVO> items) {
        OrderVO vo = toVO(o);           // 先调单参版本
        vo.setItems(items);
        return vo;
    }

    /* 用于列表/分页：仅 Order → OrderVO（无明细） */
    public static OrderVO toVO(Order o) {
        return OrderVO.builder()
                .id(o.getId())
                .orderNo(o.getOrderNo())
                .totalAmount(o.getTotalAmount())
                .paymentAmount(o.getPaymentAmount())
                .statusDesc(o.getStatus().getDesc())
                .build();
    }
}
