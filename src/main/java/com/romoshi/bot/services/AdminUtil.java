package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class AdminUtil {

    final ProductService productService;

    public SendMessage addProduct(Update update, Product product) {
        Product newProduct = productService.saveProduct(product);

        return sendMsg(update, BotStringConstant.SITE_STRING);
    }
}
