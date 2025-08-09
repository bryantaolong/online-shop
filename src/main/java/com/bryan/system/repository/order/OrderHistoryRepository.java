package com.bryan.system.repository.order;

import com.bryan.system.domain.entity.order.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderHistoryRepository
 *
 * @author Bryan Long
 */
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Integer> {
}
