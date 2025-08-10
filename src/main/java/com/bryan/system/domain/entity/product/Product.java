package com.bryan.system.domain.entity.product;

import com.bryan.system.domain.enums.ProductStatusEnum;
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
import java.time.LocalDateTime;

/**
 * Product 商品 SPU（标准产品单位）
 *
 * @author Bryan Long
 */
@Entity
@Table(name = "pms_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")                       // 逻辑删除过滤
@SQLDelete(sql = "UPDATE pms_product SET deleted = 1, updated_at = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)        // 自动填充审计字段
public class Product implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- 业务字段 ---------- */
    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long brandId;

    @Column(length = 512)
    private String description;

    @Column(length = 256)
    private String mainImage;

    @Enumerated(EnumType.ORDINAL)   // 与数据库 integer 列对应
    @Column(nullable = false)
    private ProductStatusEnum status = ProductStatusEnum.OFF_SHELF;

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
