package com.bryan.system.domain.entity.user;

import com.bryan.system.domain.enums.AddressTagEnum;
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
 * UserAddress 用户收货地址
 *
 * @author Bryan Long
 */
@Entity
@Table(name = "ums_user_address")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")                       // 逻辑删除过滤
@SQLDelete(sql = "UPDATE ums_user_address SET deleted = 1, updated_at = NOW() WHERE id = ? AND version = ?")
@EntityListeners(AuditingEntityListener.class)        // 自动填充审计字段
public class UserAddress implements Serializable {

    /* ---------- 主键 ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- 业务字段 ---------- */
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 64)
    private String receiverName;

    @Column(nullable = false, length = 20)
    private String receiverPhone;

    @Column(nullable = false, length = 32)
    private String province;

    @Column(nullable = false, length = 32)
    private String city;

    @Column(nullable = false, length = 32)
    private String district;

    @Column(nullable = false, length = 128)
    private String detailAddress;

    @Column(length = 6)
    private String postalCode;

    @Column(nullable = false)
    private Integer isDefault = 0;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private AddressTagEnum addressTag = AddressTagEnum.HOME;

    @Column(length = 32)
    private String tagName;

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
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    /* ---------- 计算属性 ---------- */
    @Transient
    public String getFullAddress() {
        return province + city + district + detailAddress;
    }
}
