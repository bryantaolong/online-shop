package com.bryan.system.domain.entity.order;

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
 * OrderItem 订单项
 *
 * @author Bryan Long
 */
@Entity
@Table(name = "oms_order_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")                       // 逻辑删除过滤
@SQLDelete(sql = "UPDATE oms_order_item SET deleted = 1, updated_at = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)        // 审计字段自动填充
public class OrderItem implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- 业务字段 ---------- */
    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false, length = 32)
    private String orderNo;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long skuId;

    @Column(nullable = false, length = 128)
    private String productName;

    @Column(length = 128)
    private String skuName;

    @Column(length = 256)
    private String productImage;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "json")
    private String specifications;

    /* ---------- 通用字段 ---------- */
    @Column(name = "deleted")
    private Integer deleted = 0;

    @Version
    @Column(name = "version")
    private Integer version = 0;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updateBy;
}
