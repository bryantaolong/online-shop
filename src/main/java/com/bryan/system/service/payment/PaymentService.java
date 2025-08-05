package com.bryan.system.service.payment;

import com.bryan.system.model.dto.PayCreateDTO;
import com.bryan.system.model.dto.RefundResult;
import com.bryan.system.model.vo.PaymentVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * PaymentService
 *
 * @author Bryan Long
 * @version 1.0
 * @since 2025/8/5
 */
public interface PaymentService {
    String prePay(PayCreateDTO dto);

    void callback(String orderNo, Map<String, String> params) throws JsonProcessingException;

    RefundResult refund(String orderNo, BigDecimal amount);

    PaymentVO getByOrderNo(String orderNo);
}
