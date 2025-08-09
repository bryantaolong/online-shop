package com.bryan.system.repository.cart;

import com.bryan.system.domain.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CartItemRepository
 *
 * @author Bryan Long
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /** 查询用户的全部有效购物车项（deleted = 0） */
    List<CartItem> findByUserIdAndDeletedOrderByCreateTimeDesc(Long userId, Integer deleted);

    /** 根据用户 + SKU 唯一查询（逻辑删除过滤） */
    Optional<CartItem> findByUserIdAndSkuIdAndDeleted(Long userId, Long skuId, Integer deleted);

    /** 物理删除某用户全部购物车（逻辑删除场景下用 update） */
    @Modifying
    @Query("update CartItem c set c.deleted = 1 where c.userId = :userId")
    void softDeleteByUserId(@Param("userId") Long userId);

    /** 物理删除某用户指定 SKU（逻辑删除场景下用 update） */
    @Modifying
    @Query("update CartItem c set c.deleted = 1 where c.userId = :userId and c.skuId = :skuId")
    void softDeleteByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Long skuId);
}
