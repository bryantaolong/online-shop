package com.bryan.system.repository.product;

import com.bryan.system.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductInfoRepository
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/7
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<Product, Long> {
}
