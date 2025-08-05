package com.bryan.system.controller;

import com.bryan.system.model.dto.SkuCreateDTO;
import com.bryan.system.model.vo.SkuVO;
import com.bryan.system.service.product.SkuService;
import com.bryan.system.model.response.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductSkuController
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@RestController
@RequestMapping("/api/sku")
@RequiredArgsConstructor
@Validated
public class ProductSkuController {

    private final SkuService skuService;

    /** 根据商品 ID 列表 SKU */
    @GetMapping("/product/{productId}")
    public Result<List<SkuVO>> listByProduct(@PathVariable Long productId) {
        return Result.success(skuService.listByProduct(productId));
    }

    /** 管理员创建 SKU */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> createSku(@RequestBody @Valid SkuCreateDTO dto) {
        return Result.success(skuService.create(dto));
    }
}
