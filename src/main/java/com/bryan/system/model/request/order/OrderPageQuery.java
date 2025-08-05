package com.bryan.system.model.request.order;

import com.bryan.system.common.enums.OrderStatusEnum;
import com.bryan.system.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OrderPageQuery
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderPageQuery extends PageRequest {
    private Long userId;          // 用户 ID（后台可空）
    private String orderNo;       // 订单号（模糊）
    private OrderStatusEnum status; // 订单状态
}
