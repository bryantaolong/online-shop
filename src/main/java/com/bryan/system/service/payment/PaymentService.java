package com.bryan.system.service.payment;

import com.bryan.system.domain.dto.PayCreateDTO;
import com.bryan.system.domain.dto.RefundResult;
import com.bryan.system.domain.vo.PaymentVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * PaymentService
 *
 * @author Bryan Long
 */
public interface PaymentService {
    String prePay(PayCreateDTO dto);

    void callback(String orderNo, Map<String, String> params) throws JsonProcessingException;

    RefundResult refund(String orderNo, BigDecimal amount);

    PaymentVO getByOrderNo(String orderNo);
}
