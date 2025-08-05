package com.bryan.system.model.entity.payment;

import com.baomidou.mybatisplus.annotation.*;
import com.bryan.system.common.enums.PayStatusEnum;
import com.bryan.system.common.enums.PayTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentInfo
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pay_payment")
public class PaymentInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String orderNo;

    @EnumValue
    private PayTypeEnum paymentType;

    private String transactionId;

    private BigDecimal paymentAmount;

    @EnumValue
    private PayStatusEnum paymentStatus;

    private LocalDateTime paymentTime;

    private String callbackContent;

    private LocalDateTime callbackTime;

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
