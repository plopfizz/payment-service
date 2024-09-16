package com.paymentService.payment.service.Dtos;

import com.paymentService.payment.service.Entities.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long orderId;
    private PaymentStatusEnum status;


}

