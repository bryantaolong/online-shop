package com.bryan.system.repository.product;

import com.bryan.system.domain.entity.product.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProductSkuRepository
 *
 * @author Bryan Long
 */
@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

    /** 根据商品 ID 查询全部 SKU */
    List<ProductSku> findByProductId(Long productId);

    /** 根据 SKU 编码查询（唯一） */
    Optional<ProductSku> findBySkuCode(String skuCode);

    /**
     * 扣减库存（乐观锁控制）
     * @return 影响行数；0 表示库存不足或版本冲突
     */
    @Modifying
    @Query("""
           UPDATE ProductSku s
           SET s.stock = s.stock - :quantity,
               s.version = s.version + 1
           WHERE s.id = :skuId
             AND s.stock >= :quantity
             AND s.version = :version
           """)
    int deductStock(@Param("skuId") Long skuId,
                    @Param("quantity") int quantity,
                    @Param("version") int version);
}
