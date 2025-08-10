package com.bryan.system.domain.entity.user;

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
 * BaseEntity
 *
 * @author Bryan Long
 */
@Entity
@Table(name = "sys_user_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")
@SQLDelete(sql = "UPDATE sys_user_role SET deleted = 1, updated_at = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)
public class UserRole implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

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
