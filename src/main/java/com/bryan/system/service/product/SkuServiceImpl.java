package com.bryan.system.service.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bryan.system.common.exception.BusinessException;
import com.bryan.system.common.exception.ResourceNotFoundException;
import com.bryan.system.mapper.ProductSkuMapper;
import com.bryan.system.model.converter.SkuConverter;
import com.bryan.system.model.dto.SkuCreateDTO;
import com.bryan.system.model.entity.product.ProductSku;
import com.bryan.system.model.vo.SkuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SkuServiceImpl
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {

    private final ProductSkuMapper skuMapper;

    @Override
    public Long create(SkuCreateDTO dto) {
        ProductSku sku = SkuConverter.toEntity(dto);
        skuMapper.insert(sku);
        return sku.getId();
    }

    @Override
    public List<SkuVO> listByProduct(Long productId) {
        List<ProductSku> list = skuMapper.selectList(
                new QueryWrapper<ProductSku>().eq("product_id", productId));
        return list.stream()
                .map(SkuConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductSku getBySkuCode(String skuCode) {
        return Optional.ofNullable(
                        skuMapper.selectOne(new QueryWrapper<ProductSku>().eq("sku_code", skuCode)))
                .orElseThrow(() -> new ResourceNotFoundException("SKU不存在"));
    }

    @Override
    @Transactional
    public void deductStock(Long skuId, int quantity) {
        ProductSku sku = skuMapper.selectById(skuId);
        if (sku.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        sku.setStock(sku.getStock() - quantity);
        skuMapper.updateById(sku);
    }
}