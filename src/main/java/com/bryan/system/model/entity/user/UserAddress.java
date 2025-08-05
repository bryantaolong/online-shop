package com.bryan.system.model.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import com.bryan.system.common.enums.AddressTagEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * UserAddress 用户收货地址
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_address")
public class UserAddress implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postalCode;

    private Integer isDefault;

    @EnumValue
    private AddressTagEnum addressTag;
    private String tagName;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;

    /** 乐观锁 */
    @Version
    private Integer version;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(exist = false)
    private String fullAddress;

    public String getFullAddress() {
        return province + city + district + detailAddress;
    }
}