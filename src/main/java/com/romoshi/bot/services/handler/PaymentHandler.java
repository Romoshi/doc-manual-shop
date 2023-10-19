package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class PaymentHandler {

    private final PaymentService paymentService;

    @Autowired
    public PaymentHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public BotApiMethod<?> processPayment(Message message) {
        return paymentService.pay(message);
    }
}
