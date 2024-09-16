package com.paymentService.payment.service.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentService.payment.service.Dtos.PaymentResponse;
import com.paymentService.payment.service.Entities.Payment;
import com.paymentService.payment.service.Entities.PaymentStatusEnum;
import com.paymentService.payment.service.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void processPayment(Long orderId, Double amount) {

        PaymentStatusEnum paymentStatus = new Random().nextDouble() > 0.5 ? PaymentStatusEnum.FAILED: PaymentStatusEnum.SUCCESS;

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus(paymentStatus);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
        JsonNode jsonNode = objectMapper.valueToTree(new PaymentResponse(orderId, paymentStatus));
        System.out.println("we are in the process payment part" +" "+LocalDateTime.now());
        kafkaTemplate.send("payment_status",jsonNode );
    }


}

