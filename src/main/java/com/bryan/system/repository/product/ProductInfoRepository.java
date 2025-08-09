package com.bryan.system.repository.product;

import com.bryan.system.domain.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductInfoRepository
 *
 * @author Bryan Long
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<Product, Long> {
}
