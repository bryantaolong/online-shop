package com.bryan.system.service.product;

import com.bryan.system.domain.dto.SkuCreateDTO;
import com.bryan.system.domain.entity.product.ProductSku;
import com.bryan.system.domain.vo.SkuVO;

import java.util.List;

/**
 * SkuService
 *
 * @author Bryan Long
 */
public interface SkuService {

    Long create(SkuCreateDTO dto);

    List<SkuVO> listByProduct(Long productId);

    ProductSku getBySkuCode(String skuCode);

    void deductStock(Long skuId, int quantity);
}
