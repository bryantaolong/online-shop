package com.bryan.system.service.product;

import com.bryan.system.exception.BusinessException;
import com.bryan.system.exception.ResourceNotFoundException;
import com.bryan.system.domain.converter.SkuConverter;
import com.bryan.system.domain.dto.SkuCreateDTO;
import com.bryan.system.domain.entity.product.ProductSku;
import com.bryan.system.domain.vo.SkuVO;
import com.bryan.system.repository.product.ProductSkuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SkuServiceImpl
 *
 * @author Bryan Long
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {

    private final ProductSkuRepository skuRepository;

    @Override
    @Transactional
    public Long create(SkuCreateDTO dto) {
        ProductSku sku = SkuConverter.toEntity(dto);
        sku = skuRepository.save(sku);
        return sku.getId();
    }

    @Override
    public List<SkuVO> listByProduct(Long productId) {
        return skuRepository.findByProductId(productId)
                .stream()
                .map(SkuConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductSku getBySkuCode(String skuCode) {
        return skuRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("SKU不存在"));
    }

    /**
     * 扣减库存（带乐观锁）
     */
    @Override
    @Transactional
    public void deductStock(Long skuId, int quantity) {
        ProductSku sku = skuRepository.findById(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("SKU不存在"));
        if (sku.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        int rows = skuRepository.deductStock(skuId, quantity, sku.getVersion());
        if (rows == 0) {
            throw new BusinessException("库存扣减失败（并发冲突或库存不足）");
        }
    }
}
