package com.bryan.system.controller;

import com.bryan.system.domain.dto.PayCreateDTO;
import com.bryan.system.domain.dto.RefundResult;
import com.bryan.system.domain.vo.PaymentVO;
import com.bryan.system.service.payment.PaymentService;
import com.bryan.system.domain.response.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * PaymentController
 *
 * @author Bryan Long
 */
@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    /** 创建支付单（前端调用） */
    @PostMapping("/prepay")
    public Result<String> prePay(@RequestBody @Valid PayCreateDTO dto) {
        return Result.success(paymentService.prePay(dto));
    }

    /** 支付回调（微信/支付宝 POST） */
    @PostMapping("/callback")
    public Result<Void> callback(@RequestParam Map<String, String> params) throws Exception {
        paymentService.callback(params.get("order_no"), params);
        return Result.success(null);
    }

    /** 退款（管理员/用户） */
    @PostMapping("/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<RefundResult> refund(@RequestParam String orderNo,
                                       @RequestParam BigDecimal amount) {
        return Result.success(paymentService.refund(orderNo, amount));
    }

    /** 查询支付单详情 */
    @GetMapping("/{orderNo}")
    public Result<PaymentVO> detail(@PathVariable String orderNo) {
        return Result.success(paymentService.getByOrderNo(orderNo));
    }
}
