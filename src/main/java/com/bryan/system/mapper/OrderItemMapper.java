package com.bryan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bryan.system.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderItemMapper
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    // 通用批量插入（XML 或注解均可）
    @Insert({
            "<script>",
            "INSERT INTO oms_order_item (order_id, order_no, product_id, sku_id, product_name, sku_name, product_image, product_price, quantity, total_price, specifications) VALUES ",
            "<foreach collection='list' item='item' separator=','>",
            "(#{item.orderId}, #{item.orderNo}, #{item.productId}, #{item.skuId}, #{item.productName}, #{item.skuName}, #{item.productImage}, #{item.productPrice}, #{item.quantity}, #{item.totalPrice}, #{item.specifications})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param("list") List<OrderItem> list);
}
