package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface Action {

    BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product);

    String getActionName();
}
