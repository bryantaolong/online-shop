package com.bryan.system.service.product;

import com.bryan.system.model.dto.SkuCreateDTO;
import com.bryan.system.model.entity.product.ProductSku;
import com.bryan.system.model.vo.SkuVO;

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
