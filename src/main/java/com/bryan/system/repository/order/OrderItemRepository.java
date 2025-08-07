package com.bryan.system.repository.order;

import com.bryan.system.model.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderItemRepository
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/7
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /** 一次性查出多个订单的明细 */
    List<OrderItem> findByOrderIdIn(List<Long> orderIds);
}
