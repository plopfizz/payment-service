package com.paymentService.payment.service.Repositories;

import com.paymentService.payment.service.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
