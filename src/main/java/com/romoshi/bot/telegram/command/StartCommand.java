package com.romoshi.bot.telegram.command;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.bot;

@Component
public class StartCommand extends Handler {
    public void handleRequest(Message request) {
        if (Objects.equals(request.getText(), CommandConstant.START_COMMAND)) {
            bot.sendMsg(request, BotStringConstant.START_STRING);
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
