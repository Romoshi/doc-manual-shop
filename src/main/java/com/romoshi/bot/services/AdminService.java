package com.romoshi.bot.services;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
public class AdminService {

    private final ProductService productService;

    @Autowired
    public AdminService(ProductService productService) {
        this.productService = productService;
    }

    public SendMessage addProduct(Message message) {
        Product defaultProduct = new Product();
        defaultProduct.setFileId(BotStringConstant.DEFAULT_PRODUCT_FILE_ID);
        defaultProduct.setName(BotStringConstant.DEFAULT_PRODUCT_NAME);
        defaultProduct.setDescription(BotStringConstant.DEFAULT_DESCRIPTION);
        defaultProduct.setPrice(BotStringConstant.DEFAULT_PRODUCT_PRICE);

        productService.saveProduct(defaultProduct);

        return sendMsg(message, BotStringConstant.ADD_STRING);
    }
}
