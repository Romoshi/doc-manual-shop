package com.romoshi.bot.services;

import com.romoshi.bot.entity.Payment;
import com.romoshi.bot.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void savePaymentInfo(String paymentId, BigDecimal amount, String currency) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setAmount(amount);
        payment.setCurrency(currency);

        paymentRepository.save(payment);
    }
}
