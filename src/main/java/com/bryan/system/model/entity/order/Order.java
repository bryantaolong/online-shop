package com.bryan.system.model.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import com.bryan.system.common.enums.OrderStatusEnum;
import com.bryan.system.common.enums.PayTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order 订单主表
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oms_order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;

    private Long userId;

    private BigDecimal totalAmount;

    private BigDecimal paymentAmount;

    private BigDecimal freightAmount;

    @EnumValue
    private PayTypeEnum paymentType;

    @EnumValue
    private OrderStatusEnum status;

    private String shippingName;

    private String shippingCode;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private String note;

    private LocalDateTime paymentTime;

    private LocalDateTime deliveryTime;

    private LocalDateTime receiveTime;

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
