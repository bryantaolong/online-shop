package com.bryan.system.repository.product;

import com.bryan.system.model.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductCategoryRepository
 *
 * @author Bryan Long
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
