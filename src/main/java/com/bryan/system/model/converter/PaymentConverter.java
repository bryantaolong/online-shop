package com.bryan.system.model.converter;

import com.bryan.system.model.entity.payment.PaymentInfo;
import com.bryan.system.model.vo.PaymentVO;

/**
 * PaymentConverter
 *
 * @author Bryan Long
 */
public class PaymentConverter {

    /** entity → VO */
    public static PaymentVO toVO(PaymentInfo entity) {
        return PaymentVO.builder()
                .id(entity.getId())
                .orderNo(entity.getOrderNo())
                .paymentType(entity.getPaymentType().getDesc())
                .transactionId(entity.getTransactionId())
                .paymentAmount(entity.getPaymentAmount())
                .statusDesc(entity.getPaymentStatus().getDesc())
                .paymentTime(entity.getPaymentTime())
                .build();
    }

    /** VO → entity（目前用不到，可留空） */
    public static PaymentInfo toEntity(PaymentVO vo) {
        return null;
    }
}
