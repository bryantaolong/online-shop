package com.bryan.system.repository.order;

import com.bryan.system.model.enums.OrderStatusEnum;
import com.bryan.system.model.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * OrderRepository
 *
 * @author Bryan Long
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
           SELECT o FROM Order o
           WHERE (:userId IS NULL OR o.userId = :userId)
             AND (:orderNo IS NULL OR o.orderNo = :orderNo)
             AND (:status IS NULL OR o.status = :status)
             AND (:createTimeStart IS NULL OR o.createTime >= :createTimeStart)
             AND (:createTimeEnd   IS NULL OR o.createTime <= :createTimeEnd)
           ORDER BY o.createTime DESC
           """)
    Page<Order> search(@Param("userId") Long userId,
                       @Param("orderNo") String orderNo,
                       @Param("status") OrderStatusEnum status,
                       @Param("createTimeStart") LocalDateTime createTimeStart,
                       @Param("createTimeEnd") LocalDateTime createTimeEnd,
                       Pageable pageable);

    Optional<Order> findByOrderNo(String orderNo);
}
