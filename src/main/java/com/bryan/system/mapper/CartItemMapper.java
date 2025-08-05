package com.bryan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bryan.system.model.entity.cart.CartItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * CartItemMapper
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {
}
