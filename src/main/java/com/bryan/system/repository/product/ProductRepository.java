package com.bryan.system.repository.product;

import com.bryan.system.model.enums.ProductStatusEnum;
import com.bryan.system.model.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 *
 * @author Bryan Long
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
           SELECT p FROM Product p
           WHERE (:name IS NULL OR p.name LIKE %:name%)
             AND (:categoryId IS NULL OR p.categoryId = :categoryId)
             AND (:brandId IS NULL OR p.brandId = :brandId)
             AND (:status IS NULL OR p.status = :status)
           ORDER BY p.id DESC
           """)
    Page<Product> search(@Param("name") String name,
                         @Param("categoryId") Long categoryId,
                         @Param("brandId") Long brandId,
                         @Param("status") ProductStatusEnum status,
                         Pageable pageable);
}
