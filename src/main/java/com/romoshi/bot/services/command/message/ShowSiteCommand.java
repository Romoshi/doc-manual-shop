package com.romoshi.bot.services.command.message;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class ShowSiteCommand implements Command {

    @Override
    public BotApiMethod<?> execute(Message message) {
        return sendMsg(message, BotStringConstant.SITE_STRING);
    }

    @Override
    public String getCommandName() {
        return CommandConstant.SHOW_SITE_COMMAND;
    }
}
