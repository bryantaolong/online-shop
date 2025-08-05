package com.bryan.system.service.cart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bryan.system.common.exception.ResourceNotFoundException;
import com.bryan.system.mapper.CartItemMapper;
import com.bryan.system.model.converter.CartConverter;
import com.bryan.system.model.converter.CartItemConverter;
import com.bryan.system.model.dto.AddCartItemDTO;
import com.bryan.system.model.entity.cart.CartItem;
import com.bryan.system.model.vo.CartItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CartServiceImpl
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemMapper cartMapper;

    @Override
    public List<CartItemVO> listByUser(Long userId) {
        return cartMapper.selectList(
                        new QueryWrapper<CartItem>()
                                .eq("user_id", userId)
                                .eq("deleted", 0)
                                .orderByDesc("create_time"))
                .stream()
                .map(CartItemConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Long userId, AddCartItemDTO dto) {
        QueryWrapper<CartItem> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("sku_id", dto.getSkuId());

        CartItem exist = cartMapper.selectOne(qw);
        if (exist != null) {
            exist.setQuantity(exist.getQuantity() + dto.getQuantity());
            cartMapper.updateById(exist);
        } else {
            CartItem item = CartConverter.from(dto, userId);
            cartMapper.insert(item);
        }
    }

    @Override
    public void updateQuantity(Long userId, Long skuId, int quantity) {
        QueryWrapper<CartItem> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("sku_id", skuId);
        CartItem item = Optional.ofNullable(cartMapper.selectOne(qw))
                .orElseThrow(() -> new ResourceNotFoundException("购物车项不存在"));
        item.setQuantity(quantity);
        cartMapper.updateById(item);
    }

    @Override
    public void removeItem(Long userId, Long skuId) {
        QueryWrapper<CartItem> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("sku_id", skuId);
        cartMapper.delete(qw);
    }

    @Override
    public void clear(Long userId) {
        cartMapper.delete(new QueryWrapper<CartItem>().eq("user_id", userId));
    }
}
