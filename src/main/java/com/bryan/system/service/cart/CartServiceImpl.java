package com.bryan.system.service.cart;

import com.bryan.system.exception.ResourceNotFoundException;
import com.bryan.system.domain.converter.CartConverter;
import com.bryan.system.domain.converter.CartItemConverter;
import com.bryan.system.domain.dto.AddCartItemDTO;
import com.bryan.system.domain.entity.cart.CartItem;
import com.bryan.system.domain.vo.CartItemVO;
import com.bryan.system.repository.cart.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CartServiceImpl
 *
 * @author Bryan Long
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartRepository;

    @Override
    public List<CartItemVO> listByUser(Long userId) {
        return cartRepository.findByUserIdAndDeletedOrderByCreateTimeDesc(userId, 0)
                .stream()
                .map(CartItemConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addItem(Long userId, AddCartItemDTO dto) {
        cartRepository.findByUserIdAndSkuIdAndDeleted(userId, dto.getSkuId(), 0)
                .ifPresentOrElse(
                        // 已存在 -> 数量累加
                        item -> {
                            item.setQuantity(item.getQuantity() + dto.getQuantity());
                        },
                        // 不存在 -> 新建
                        () -> cartRepository.save(CartConverter.from(dto, userId))
                );
    }

    @Override
    @Transactional
    public void updateQuantity(Long userId, Long skuId, int quantity) {
        CartItem item = cartRepository.findByUserIdAndSkuIdAndDeleted(userId, skuId, 0)
                .orElseThrow(() -> new ResourceNotFoundException("购物车项不存在"));
        item.setQuantity(quantity);
        cartRepository.save(item);
    }

    @Override
    @Transactional
    public void removeItem(Long userId, Long skuId) {
        cartRepository.softDeleteByUserIdAndSkuId(userId, skuId);
    }

    @Override
    @Transactional
    public void clear(Long userId) {
        cartRepository.softDeleteByUserId(userId);
    }
}
