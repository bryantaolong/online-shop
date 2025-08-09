package com.bryan.system.service.order;

import com.bryan.system.model.dto.CreateOrderDTO;
import com.bryan.system.model.entity.order.Order;
import com.bryan.system.model.request.order.OrderSearchRequest;
import com.bryan.system.model.vo.OrderVO;
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
