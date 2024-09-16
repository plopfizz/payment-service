package com.paymentService.payment.service.Config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentService.payment.service.Entities.OutBoxOrderEntity;
import com.paymentService.payment.service.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {
    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "process_payment", groupId = "payment_group")
    public void handleProcessPayment(JsonNode orderEvent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OutBoxOrderEntity order = objectMapper.treeToValue(orderEvent, OutBoxOrderEntity.class);
            Long orderId = order.getOrderId();
            Double amount = order.getPrice();
            System.out.println("we are in the process payment part in event Listener");
            paymentService.processPayment(orderId, amount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
