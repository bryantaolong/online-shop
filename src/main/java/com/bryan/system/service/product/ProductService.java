package com.bryan.system.service.product;

import com.bryan.system.domain.dto.ProductCreateDTO;
import com.bryan.system.domain.request.product.ProductSearchRequest;
import com.bryan.system.domain.vo.ProductVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ProductService
 *
 * @author Bryan Long
 */
public interface ProductService {
    Page<ProductVO> searchProducts(ProductSearchRequest req, Pageable pageable);

    ProductVO getById(Long id);

    Long create(ProductCreateDTO product);

    void update(Long id, ProductCreateDTO  product);

    void shelf(Long id);

    void offShelf(Long id);
}
