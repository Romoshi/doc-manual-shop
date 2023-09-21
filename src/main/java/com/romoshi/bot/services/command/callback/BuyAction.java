package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class BuyAction implements Action {
    final ProductService productService;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        try {

        } catch (NullPointerException ex) {
            return sendMsg(callbackQuery.getMessage(),"Файл не найден");
        }
        return null;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_PAY;
    }

    @Override
    public BotApiMethod<?> update(Message message, Product product) {
        return null;
    }
}
