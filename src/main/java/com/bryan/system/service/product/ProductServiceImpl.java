package com.bryan.system.service.product;

import com.bryan.system.model.enums.ProductStatusEnum;
import com.bryan.system.exception.ResourceNotFoundException;
import com.bryan.system.model.converter.ProductConverter;
import com.bryan.system.model.dto.ProductCreateDTO;
import com.bryan.system.model.entity.product.Product;
import com.bryan.system.model.request.product.ProductSearchRequest;
import com.bryan.system.model.vo.ProductVO;
import com.bryan.system.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProductServiceImpl
 *
 * @author Bryan Long
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Long create(ProductCreateDTO dto) {
        Product product = ProductConverter.toEntity(dto);
        product.setStatus(ProductStatusEnum.OFF_SHELF);
        product = productRepository.save(product);
        return product.getId();
    }

    @Override
    public ProductVO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        return ProductConverter.toVO(product);
    }

    @Override
    public Page<ProductVO> searchProducts(ProductSearchRequest req, Pageable pageable) {
        Page<Product> page = productRepository.search(
                req.getName(),
                req.getCategoryId(),
                req.getBrandId(),
                req.getStatus(),
                pageable);
        return page.map(ProductConverter::toVO);
    }

    @Override
    public void update(Long id, ProductCreateDTO dto) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        Product dtoEntity = ProductConverter.toEntity(dto);
        dtoEntity.setId(id);           // 保持主键
        dtoEntity.setVersion(entity.getVersion()); // 乐观锁版本需显式传递
        productRepository.save(dtoEntity);
    }

    @Override
    public void shelf(Long id) {
        changeStatus(id, ProductStatusEnum.ON_SHELF);
    }

    @Override
    public void offShelf(Long id) {
        changeStatus(id, ProductStatusEnum.OFF_SHELF);
    }

    private void changeStatus(Long id, ProductStatusEnum status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品不存在"));
        product.setStatus(status);
        productRepository.save(product);
    }
}
