package com.bryan.system.repository.pay;

import com.bryan.system.domain.enums.PayStatusEnum;
import com.bryan.system.domain.entity.payment.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * PaymentRepository
 *
 * @author Bryan Long
 */
@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

    /** 按订单号查询支付单（订单号唯一） */
    Optional<PaymentInfo> findByOrderNo(String orderNo);

    /**
     * 幂等更新支付成功信息（防止重复回调）
     * @return 影响行数；0 表示已支付或记录不存在
     */
    @Modifying
    @Query("""
           UPDATE PaymentInfo p
           SET p.paymentStatus = :status,
               p.paymentTime   = :paymentTime,
               p.transactionId = :transactionId,
               p.callbackContent = :callbackContent
           WHERE p.orderNo = :orderNo
             AND p.paymentStatus <> :status
           """)
    int markPaid(@Param("orderNo") String orderNo,
                 @Param("status") PayStatusEnum status,
                 @Param("paymentTime") LocalDateTime paymentTime,
                 @Param("transactionId") String transactionId,
                 @Param("callbackContent") String callbackContent);
}
