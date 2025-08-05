package com.bryan.system.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bryan.system.model.dto.ProductCreateDTO;
import com.bryan.system.model.entity.product.Product;
import com.bryan.system.model.request.product.ProductPageQuery;
import com.bryan.system.model.vo.ProductVO;

/**
 * ProductService
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
public interface ProductService {
    Page<ProductVO> pageProducts(ProductPageQuery query);

    ProductVO getById(Long id);

    Long create(ProductCreateDTO product);

    void update(Long id, ProductCreateDTO  product);

    void shelf(Long id);

    void offShelf(Long id);
}
