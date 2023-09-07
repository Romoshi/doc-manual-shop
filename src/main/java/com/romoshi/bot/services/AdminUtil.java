package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class AdminUtil {

    final ProductService productService;

    @Value("${product.name}")
    private String productName;

    @Value("${product.price}")
    private int productPrice;

    public SendMessage addProduct(Update update) {
        Product defaultProduct = new Product();
        defaultProduct.setName(productName);
        defaultProduct.setPrice(productPrice);

        productService.saveProduct(defaultProduct);

        return sendMsg(update, BotStringConstant.ADD_STRING);
    }
}
