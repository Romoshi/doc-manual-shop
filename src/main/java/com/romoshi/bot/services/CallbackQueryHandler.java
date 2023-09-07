package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();
    final ProductService productService;
    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        List<Product> products = productService.getAllProducts();


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());

        for(Product product : products) {
            String button = "button_" + product.getId();
            if(button.equals(data)) {
                sendMessage.setText(product.getName());
                sendMessage.setReplyMarkup(inlineKeyboardMaker.getPayButton(product.getPrice(),
                        product.getId().toString()));
            }
        }

        return sendMessage;
    }
}
