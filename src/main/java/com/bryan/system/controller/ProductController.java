package com.bryan.system.controller;

import com.bryan.system.model.dto.ProductCreateDTO;
import com.bryan.system.model.request.product.ProductSearchRequest;
import com.bryan.system.model.vo.ProductVO;
import com.bryan.system.service.product.ProductService;
import com.bryan.system.model.response.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品(SPU)控制器
 *
 * @author Bryan Long
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    /**
     * 分页查询商品列表
     */
    @GetMapping("/search")
    public Result<Page<ProductVO>> search(
            @Valid ProductSearchRequest req,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.success(productService.searchProducts(req, pageable));
    }

    /**
     * 根据 ID 查询商品详情
     */
    @GetMapping("/{id}")
    public Result<ProductVO> getProductById(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    /**
     * 创建商品（管理员）
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> createProduct(@RequestBody @Valid ProductCreateDTO dto) {
        return Result.success(productService.create(dto));
    }

    /**
     * 更新商品（管理员）
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updateProduct(@PathVariable Long id,
                                      @RequestBody @Valid ProductCreateDTO dto) {
        productService.update(id, dto);
        return Result.success(null);
    }

    /**
     * 商品上架（管理员）
     */
    @PutMapping("/{id}/on-shelf")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> onShelf(@PathVariable Long id) {
        productService.shelf(id);
        return Result.success(null);
    }

    /**
     * 商品下架（管理员）
     */
    @PutMapping("/{id}/off-shelf")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> offShelf(@PathVariable Long id) {
        productService.offShelf(id);
        return Result.success(null);
    }
}
