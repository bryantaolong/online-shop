package com.bryan.system.model.entity.payment;

import com.bryan.system.common.enums.PayStatusEnum;
import com.bryan.system.common.enums.PayTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentInfo
 *
 * @author Bryan Long
 * @since 2025/8/1
 */
@Entity
@Table(name = "pay_payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")
@SQLDelete(sql = "UPDATE pay_payment SET deleted = 1, update_time = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)
public class PaymentInfo implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- 业务字段 ---------- */
    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false, length = 64)
    private String orderNo;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_type", nullable = false)
    private PayTypeEnum paymentType;

    @Column(length = 128)
    private String transactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_status", nullable = false)
    private PayStatusEnum paymentStatus;

    private LocalDateTime paymentTime;

    @Column(columnDefinition = "text")
    private String callbackContent;

    private LocalDateTime callbackTime;

    /* ---------- 通用字段 ---------- */
    @Column(nullable = false)
    private Integer deleted = 0;

    @Version
    private Integer version = 0;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @CreatedBy
    private String createBy;

    @LastModifiedBy
    private String updateBy;
}