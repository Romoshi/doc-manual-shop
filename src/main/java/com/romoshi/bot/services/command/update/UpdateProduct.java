package com.romoshi.bot.services.command.update;

import com.romoshi.bot.models.Product;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UpdateProduct {

    BotApiMethod<?> update(Message message, Product product);

    String getUpdateName();
}
