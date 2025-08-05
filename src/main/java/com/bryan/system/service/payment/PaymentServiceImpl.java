package com.bryan.system.service.payment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bryan.system.common.enums.OrderStatusEnum;
import com.bryan.system.common.enums.PayStatusEnum;
import com.bryan.system.common.exception.BusinessException;
import com.bryan.system.common.exception.ResourceNotFoundException;
import com.bryan.system.mapper.PaymentInfoMapper;
import com.bryan.system.model.converter.PaymentConverter;
import com.bryan.system.model.dto.PayCreateDTO;
import com.bryan.system.model.dto.RefundResult;
import com.bryan.system.model.entity.order.Order;
import com.bryan.system.model.entity.payment.PaymentInfo;
import com.bryan.system.model.vo.OrderVO;
import com.bryan.system.model.vo.PaymentVO;
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

    private final PaymentInfoMapper paymentMapper;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Override
    public String prePay(PayCreateDTO dto) {
        OrderVO orderVO = orderService.getByOrderNo(dto.getOrderNo());
        if (orderVO.getStatusDesc().equals(OrderStatusEnum.PENDING_PAYMENT.getDesc())) {
            throw new BusinessException("订单状态异常");
        }
        // TODO 调用第三方 SDK
        String prepayId = "1";
        PaymentInfo entity = PaymentInfo.builder()
                .orderId(orderVO.getId())
                .orderNo(orderVO.getOrderNo())
                .paymentType(dto.getType())
                .paymentAmount(orderVO.getPaymentAmount())
                .paymentStatus(PayStatusEnum.WAITING)
                .build();
        paymentMapper.insert(entity);
        return prepayId;
    }

    @Override
    @Transactional
    public void callback(String orderNo, Map<String, String> params) throws JsonProcessingException {
        // 1. 更新支付记录
        PaymentInfo entity = paymentMapper.selectOne(
                new QueryWrapper<PaymentInfo>().eq("order_no", orderNo));
        if (entity == null || entity.getPaymentStatus() == PayStatusEnum.SUCCESS) {
            return;
        }
        entity.setPaymentStatus(PayStatusEnum.SUCCESS);
        entity.setPaymentTime(LocalDateTime.now());
        entity.setTransactionId(params.get("transaction_id"));
        entity.setCallbackContent(objectMapper.writeValueAsString(params));
        paymentMapper.updateById(entity);

        // 2. 更新订单（entity，不要 VO）
        Order orderEntity = Order.builder()
                .id(entity.getOrderId())
                .status(OrderStatusEnum.PAID)
                .paymentTime(LocalDateTime.now())
                .build();
        orderService.update(orderEntity);
    }

    @Override
    public RefundResult refund(String orderNo, BigDecimal amount) {
        // TODO 退款逻辑
        return new RefundResult();
    }

    @Override
    public PaymentVO getByOrderNo(String orderNo) {
        PaymentInfo entity = paymentMapper.selectOne(
                new QueryWrapper<PaymentInfo>().eq("order_no", orderNo));
        if (entity == null) {
            throw new ResourceNotFoundException("支付单不存在");
        }
        return PaymentConverter.toVO(entity);
    }
}
