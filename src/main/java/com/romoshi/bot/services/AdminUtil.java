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

    public SendMessage addProduct(Update update) {
        Product defaultProduct = new Product();
        defaultProduct.setName(BotStringConstant.PRODUCT_NAME);
        defaultProduct.setPrice(BotStringConstant.DEFAULT_PRODUCT_PRICE);

        productService.saveProduct(defaultProduct);

        return sendMsg(update, BotStringConstant.ADD_STRING);
    }
}
