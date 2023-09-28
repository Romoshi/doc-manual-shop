package com.romoshi.bot.services.command.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {
    BotApiMethod<?> execute(Message message);

    String getCommandName();
}
