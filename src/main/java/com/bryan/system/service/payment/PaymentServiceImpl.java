package com.bryan.system.service.payment;

import com.bryan.system.common.enums.OrderStatusEnum;
import com.bryan.system.common.enums.PayStatusEnum;
import com.bryan.system.common.exception.BusinessException;
import com.bryan.system.common.exception.ResourceNotFoundException;
import com.bryan.system.model.converter.PaymentConverter;
import com.bryan.system.model.dto.PayCreateDTO;
import com.bryan.system.model.dto.RefundResult;
import com.bryan.system.model.entity.order.Order;
import com.bryan.system.model.entity.payment.PaymentInfo;
import com.bryan.system.model.vo.OrderVO;
import com.bryan.system.model.vo.PaymentVO;
import com.bryan.system.repository.pay.PaymentInfoRepository;
import com.bryan.system.service.order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * PaymentServiceImpl
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentInfoRepository paymentRepository;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Override
    public String prePay(PayCreateDTO dto) {
        OrderVO orderVO = orderService.getByOrderNo(dto.getOrderNo());

        // 状态校验
        if (!orderVO.getStatusDesc().equals(OrderStatusEnum.PENDING_PAYMENT.getDesc())) {
            throw new BusinessException("订单状态异常");
        }

        // TODO 调用第三方 SDK 获取 prepayId
        String prepayId = "1";

        PaymentInfo entity = PaymentInfo.builder()
                .orderId(orderVO.getId())
                .orderNo(orderVO.getOrderNo())
                .paymentType(dto.getType())
                .paymentAmount(orderVO.getPaymentAmount())
                .paymentStatus(PayStatusEnum.WAITING)
                .build();

        paymentRepository.save(entity);
        return prepayId;
    }

    @Override
    @Transactional
    public void callback(String orderNo, Map<String, String> params)
            throws JsonProcessingException {
        // 1. 幂等更新支付单
        int rows = paymentRepository.markPaid(
                orderNo,
                PayStatusEnum.SUCCESS,
                LocalDateTime.now(),
                params.get("transaction_id"),
                objectMapper.writeValueAsString(params));

        // 2. 已支付或不存在直接返回
        if (rows == 0) {
            log.warn("重复回调或支付单不存在，orderNo={}", orderNo);
            return;
        }

        // 3. 更新订单状态
        PaymentInfo payment = paymentRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new ResourceNotFoundException("支付单不存在"));
        Order orderEntity = Order.builder()
                .id(payment.getOrderId())
                .status(OrderStatusEnum.PAID)
                .paymentTime(LocalDateTime.now())
                .build();
        orderService.update(orderEntity);
    }

    @Override
    public RefundResult refund(String orderNo, BigDecimal amount) {
        // TODO 第三方退款 SDK 集成
        return new RefundResult();
    }

    @Override
    public PaymentVO getByOrderNo(String orderNo) {
        return paymentRepository.findByOrderNo(orderNo)
                .map(PaymentConverter::toVO)
                .orElseThrow(() -> new ResourceNotFoundException("支付单不存在"));
    }
}
