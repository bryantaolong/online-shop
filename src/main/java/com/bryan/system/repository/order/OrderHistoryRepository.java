package com.bryan.system.repository.order;

import com.bryan.system.model.entity.order.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderHistoryRepository
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/7
 */
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Integer> {
}
