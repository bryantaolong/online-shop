package com.bryan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bryan.system.model.entity.order.OrderHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderHistoryMapper
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Mapper
public interface OrderHistoryMapper extends BaseMapper<OrderHistory> {
}
