package com.bryan.system.service.cart;

import com.bryan.system.model.dto.AddCartItemDTO;
import com.bryan.system.model.vo.CartItemVO;

import java.util.List;

/**
 * CartService
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
public interface CartService {
    List<CartItemVO> listByUser(Long userId);

    void addItem(Long userId, AddCartItemDTO dto);

    void updateQuantity(Long userId, Long skuId, int quantity);

    void removeItem(Long userId, Long skuId);

    void clear(Long userId);
}
