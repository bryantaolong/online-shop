package com.bryan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bryan.system.model.entity.payment.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * PaymentInfoMapper
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {
}
