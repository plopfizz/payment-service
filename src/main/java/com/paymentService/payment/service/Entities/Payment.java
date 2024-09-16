package com.paymentService.payment.service.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private Long orderId;
    private PaymentStatusEnum status;
    private Double amount;
    private LocalDateTime paymentDate;

}
