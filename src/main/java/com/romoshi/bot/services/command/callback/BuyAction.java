package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class BuyAction implements Action {

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        return null;
    }

    @Override
    public String getActionName() {
        return null;
    }

    @Override
    public BotApiMethod<?> update(Message message, Product product) {
        return null;
    }
}
