package com.bryan.system.model.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import com.bryan.system.common.enums.GenderEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * UserProfile 用户详情
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_profile")
public class UserProfile implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    private String realName;

    @EnumValue
    private GenderEnum gender;

    private LocalDate birthday;

    private String avatar;

    private String idCard;

    private String idCardFront;

    private String idCardBack;

    private String personalSign;

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
}
