package com.bryan.system.model.entity.product;

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
 * Brand 商品品牌
 *
 * @author Bryan Long
 * @since 2025/8/1
 */
@Entity
@Table(name = "pms_brand")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")                       // 逻辑删除过滤
@SQLDelete(sql = "UPDATE pms_brand SET deleted = 1, update_time = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)        // 自动填充审计字段
public class Brand implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    /* ---------- 业务字段 ---------- */
    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 256)
    private String logo;

    @Column(length = 512)
    private String description;

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