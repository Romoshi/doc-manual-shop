package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class AdminService {

    final ProductService productService;

    public SendMessage addProduct(Message message) {
        Product defaultProduct = new Product();
        defaultProduct.setName(BotStringConstant.DEFAULT_PRODUCT_NAME);
        defaultProduct.setDescription(BotStringConstant.DEFAULT_DESCRIPTION);
        defaultProduct.setPrice(BotStringConstant.DEFAULT_PRODUCT_PRICE);

        productService.saveProduct(defaultProduct);

        return sendMsg(message, BotStringConstant.ADD_STRING);
    }
}
