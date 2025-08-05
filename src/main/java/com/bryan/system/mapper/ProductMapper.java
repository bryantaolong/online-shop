package com.bryan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bryan.system.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * ProductMapper
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
