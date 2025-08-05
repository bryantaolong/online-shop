package com.bryan.system.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryan.system.model.dto.CreateOrderDTO;
import com.bryan.system.model.entity.order.Order;
import com.bryan.system.model.request.order.OrderPageQuery;
import com.bryan.system.model.vo.OrderVO;

/**
 * OrderService
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
public interface OrderService {

    String create(CreateOrderDTO dto);

    OrderVO getById(Long id);

    OrderVO getByOrderNo(String orderNo);

    Page<OrderVO> pageOrders(OrderPageQuery q);

    void update(Order order);

    void cancel(String orderNo);

    void confirmReceive(String orderNo);
}
