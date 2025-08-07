package com.bryan.system.model.request.order;

import com.bryan.system.common.enums.OrderStatusEnum;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * OrderPageQuery
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Getter
@Setter
public class OrderSearchRequest {

    @PositiveOrZero(message = "用户ID必须≥0")
    private Long userId;

    @Size(max = 32, message = "订单号长度超限")
    private String orderNo;

    private OrderStatusEnum status;   // null=不限

    /* 下面可按需要继续加时间区间、金额区间等 */

    @PastOrPresent(message = "创建开始时间不能是未来")
    private LocalDateTime createTimeStart;

    @PastOrPresent(message = "创建结束时间不能是未来")
    private LocalDateTime createTimeEnd;

    @AssertTrue(message = "创建时间范围不合法")
    public boolean isCreateTimeRangeValid() {
        if (createTimeStart == null || createTimeEnd == null) return true;
        return !createTimeEnd.isBefore(createTimeStart);
    }
}
