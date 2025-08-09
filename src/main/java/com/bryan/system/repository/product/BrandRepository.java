package com.bryan.system.repository.product;

import com.bryan.system.model.entity.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BrandRepository
 *
 * @author Bryan Long
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
