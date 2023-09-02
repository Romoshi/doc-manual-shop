package com.romoshi.bot.telegram.command;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.bot;

@Component
public class DefaultCommand extends Handler {
    public void handleRequest(Message request) {
        bot.sendMsg(request, BotStringConstant.DEFAULT_STRING);
    }
}
