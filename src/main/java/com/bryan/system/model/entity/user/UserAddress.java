package com.bryan.system.model.entity.user;

import com.bryan.system.common.enums.AddressTagEnum;
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
 * @since 2025/8/1
 */
@Entity
@Table(name = "ums_user_address")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = 0")                       // 逻辑删除过滤
@SQLDelete(sql = "UPDATE ums_user_address SET deleted = 1, update_time = NOW() WHERE id = ? AND version = ?")
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

    /* ---------- 计算属性 ---------- */
    @Transient
    public String getFullAddress() {
        return province + city + district + detailAddress;
    }
}