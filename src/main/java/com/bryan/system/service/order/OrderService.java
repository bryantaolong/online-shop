package com.bryan.system.service.order;

import com.bryan.system.domain.dto.CreateOrderDTO;
import com.bryan.system.domain.entity.order.Order;
import com.bryan.system.domain.request.order.OrderSearchRequest;
import com.bryan.system.domain.vo.OrderVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * OrderService
 *
 * @author Bryan Long
 */
public interface OrderService {

    String create(CreateOrderDTO dto);

    OrderVO getById(Long id);

    OrderVO getByOrderNo(String orderNo);

    Page<OrderVO> searchOrders(OrderSearchRequest req, Pageable pageable);

    void update(Order order);

    void cancel(String orderNo);

    void confirmReceive(String orderNo);
}
