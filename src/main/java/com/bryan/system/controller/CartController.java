package com.bryan.system.controller;

import com.bryan.system.model.dto.AddCartItemDTO;
import com.bryan.system.model.vo.CartItemVO;
import com.bryan.system.service.cart.CartService;
import com.bryan.system.model.response.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CartController
 *
 * @author Bryan Long
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public Result<List<CartItemVO>> list(@AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername()); // 或从 AuthService 取
        return Result.success(cartService.listByUser(userId));
    }

    @PostMapping
    public Result<Void> add(@AuthenticationPrincipal UserDetails user,
                            @RequestBody @Valid AddCartItemDTO dto) {
        cartService.addItem(Long.valueOf(user.getUsername()), dto);
        return Result.success(null);
    }

    @PutMapping("/{skuId}/quantity/{quantity}")
    public Result<Void> updateQty(@AuthenticationPrincipal UserDetails user,
                                  @PathVariable Long skuId,
                                  @PathVariable Integer quantity) {
        cartService.updateQuantity(Long.valueOf(user.getUsername()), skuId, quantity);
        return Result.success(null);
    }

    @DeleteMapping("/{skuId}")
    public Result<Void> remove(@AuthenticationPrincipal UserDetails user,
                               @PathVariable Long skuId) {
        cartService.removeItem(Long.valueOf(user.getUsername()), skuId);
        return Result.success(null);
    }

    @DeleteMapping("/clear")
    public Result<Void> clear(@AuthenticationPrincipal UserDetails user) {
        cartService.clear(Long.valueOf(user.getUsername()));
        return Result.success(null);
    }
}
