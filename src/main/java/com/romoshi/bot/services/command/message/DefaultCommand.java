package com.romoshi.bot.services.command.message;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class DefaultCommand implements Command {
    @Override
    public BotApiMethod<?> execute(Message message) {
        return sendMsg(message, BotStringConstant.DEFAULT_STRING);
    }

    @Override
    public String getCommandName() {
        return null;
    }
}
