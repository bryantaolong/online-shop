package com.bryan.system.repository.order;

import com.bryan.system.domain.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderItemRepository
 *
 * @author Bryan Long
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /** 一次性查出多个订单的明细 */
    List<OrderItem> findByOrderIdIn(List<Long> orderIds);
}
