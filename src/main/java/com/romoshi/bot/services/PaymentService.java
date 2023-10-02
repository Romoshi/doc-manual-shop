package com.romoshi.bot.services;

import com.romoshi.bot.entity.Payment;
import com.romoshi.bot.entity.Product;
import com.romoshi.bot.repositories.PaymentRepository;
import com.romoshi.bot.services.file.FileSenderService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;

import java.math.BigDecimal;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final FileSenderService fileSenderService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ProductService productService,
                          FileSenderService fileSenderService) {
        this.paymentRepository = paymentRepository;
        this.productService = productService;
        this.fileSenderService = fileSenderService;
    }

    public void savePaymentInfo(String paymentId, BigDecimal amount, String currency) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setAmount(amount);
        payment.setCurrency(currency);

        paymentRepository.save(payment);
    }

    public BotApiMethod<?> pay(Message message) {
        SuccessfulPayment successfulPayment = message.getSuccessfulPayment();

        savePaymentInfo(successfulPayment.getTelegramPaymentChargeId(),
                BigDecimal.valueOf(successfulPayment.getTotalAmount()),
                successfulPayment.getCurrency());

        String chatId = message.getChatId().toString();

        Product product = productService.getProductById(Long.parseLong(successfulPayment.getInvoicePayload()));

        try {
            fileSenderService.sendFileToChat(chatId, product.getFileId());
            return sendMsg(message, BotStringConstant.SALE_STRING);
        } catch (NullPointerException ex) {
            log.error("Файл не найден", ex);
        }

        return null;
    }
}
