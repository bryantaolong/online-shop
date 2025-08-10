package com.bryan.system.domain.entity.product;

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
 * ProductCategory 商品分类
 *
 * @author Bryan Long
 */
@Entity
@Table(name = "pms_category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")                       // 逻辑删除过滤
@SQLDelete(sql = "UPDATE pms_category SET deleted = 1, updated_at = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)        // 自动填充审计字段
public class ProductCategory implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- 业务字段 ---------- */
    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Long parentId = 0L;   // 默认根节点

    @Column(nullable = false)
    private Integer level = 0;    // 默认 0 级

    @Column(nullable = false)
    private Integer sortOrder = 0;

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
