package com.bryan.system.controller;

import com.bryan.system.domain.dto.CreateOrderDTO;
import com.bryan.system.domain.request.order.OrderSearchRequest;
import com.bryan.system.domain.vo.OrderVO;
import com.bryan.system.service.order.OrderService;
import com.bryan.system.domain.response.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * OrderController
 *
 * @author Bryan Long
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    /** 用户下单 */
    @PostMapping
    public Result<String> create(@AuthenticationPrincipal UserDetails user,
                                 @RequestBody @Valid CreateOrderDTO dto) {
        dto.setUserId(Long.valueOf(user.getUsername()));
        return Result.success(orderService.create(dto));
    }

    /** 用户分页查看自己的订单 */
    @GetMapping("/my")
    public Result<Page<OrderVO>> myOrders(@Valid OrderSearchRequest req,
                                          @PageableDefault(sort = "createTime", direction = DESC) Pageable pageable,
                                          @AuthenticationPrincipal UserDetails user) {
        req.setUserId(Long.valueOf(user.getUsername()));
        return Result.success(orderService.searchOrders(req, pageable));
    }

    /** 管理员分页查看全部订单 */
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Page<OrderVO>> pageOrders(@Valid OrderSearchRequest req,
                                            @PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
        return Result.success(orderService.searchOrders(req, pageable));
    }

    /** 用户/管理员查看订单详情 */
    @GetMapping("/{orderNo}")
    public Result<OrderVO> detail(@PathVariable String orderNo) {
        return Result.success(orderService.getByOrderNo(orderNo));
    }

    /** 用户取消订单（仅待付款状态可取消） */
    @PutMapping("/{orderNo}/cancel")
    public Result<Void> cancel(@PathVariable String orderNo,
                               @AuthenticationPrincipal UserDetails user) {
        orderService.cancel(orderNo);
        return Result.success(null);
    }

    /** 用户确认收货（仅已发货状态可确认） */
    @PutMapping("/{orderNo}/receive")
    public Result<Void> receive(@PathVariable String orderNo,
                                @AuthenticationPrincipal UserDetails user) {
        orderService.confirmReceive(orderNo);
        return Result.success(null);
    }
}
