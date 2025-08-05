package com.bryan.system.service.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryan.system.common.enums.ProductStatusEnum;
import com.bryan.system.common.exception.ResourceNotFoundException;
import com.bryan.system.mapper.ProductMapper;
import com.bryan.system.model.converter.ProductConverter;
import com.bryan.system.model.dto.ProductCreateDTO;
import com.bryan.system.model.entity.product.Product;
import com.bryan.system.model.request.product.ProductPageQuery;
import com.bryan.system.model.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * ProductServiceImpl
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public Long create(ProductCreateDTO dto) {
        Product product = ProductConverter.toEntity(dto);
        product.setStatus(ProductStatusEnum.OFF_SHELF); // 默认下架
        productMapper.insert(product);
        return product.getId();
    }

    @Override
    public ProductVO getById(Long id) {
        Product p = Optional.ofNullable(productMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        return ProductConverter.toVO(p);
    }

    @Override
    public Page<ProductVO> pageProducts(ProductPageQuery q) {
        QueryWrapper<Product> qw = new QueryWrapper<>();
        qw.like(StringUtils.hasText(q.getName()), "name", q.getName())
                .eq(q.getCategoryId() != null, "category_id", q.getCategoryId())
                .eq(q.getBrandId() != null, "brand_id", q.getBrandId())
                .eq(q.getStatus() != null, "status", q.getStatus())
                .orderByDesc("id");

        Page<Product> page = productMapper.selectPage(
                new Page<>(q.getPageNum(), q.getPageSize()), qw);

        return (Page<ProductVO>) page.convert(ProductConverter::toVO);
    }

    @Override
    public void update(Long id, ProductCreateDTO dto) {
        Product product = ProductConverter.toEntity(dto);
        product.setId(id);
        productMapper.updateById(product);
    }

    @Override
    public void shelf(Long id) {
        this.changeStatus(id, ProductStatusEnum.ON_SHELF);
    }

    @Override
    public void offShelf(Long id) {
        this.changeStatus(id, ProductStatusEnum.OFF_SHELF);
    }

    private void changeStatus(Long id, ProductStatusEnum status) {
        Product entity = Optional.ofNullable(productMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        entity.setStatus(status);
        productMapper.updateById(entity);
    }
}
