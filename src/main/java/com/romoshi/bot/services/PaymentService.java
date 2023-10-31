package com.romoshi.bot.services;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.file.FileSenderService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@Slf4j
public class PaymentService {
    private final ProductService productService;
    private final FileSenderService fileSenderService;

    @Autowired
    public PaymentService(ProductService productService,
                          FileSenderService fileSenderService) {
        this.productService = productService;
        this.fileSenderService = fileSenderService;
    }

    public BotApiMethod<?> pay(Message message) {
        SuccessfulPayment successfulPayment = message.getSuccessfulPayment();

        String chatId = message.getChatId().toString();

        Product product = productService.getProductById(Long.parseLong(successfulPayment.getInvoicePayload()));

        try {
            fileSenderService.sendFileToChat(chatId, product.getFileId());
            return sendMsg(message, BotStringConstant.SALE_STRING);
        } catch (NullPointerException ex) {
            log.error("Файл не найден", ex);
            return sendMsg(message, BotStringConstant.FILE_CANT_FOUND);
        }
    }
}
