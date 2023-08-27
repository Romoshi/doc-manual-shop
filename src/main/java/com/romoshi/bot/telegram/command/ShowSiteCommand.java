package com.romoshi.bot.telegram.command;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.bot;

@Component
public class ShowSiteCommand extends Handler{

    public void handleRequest(Message request) {
        if (Objects.equals(request.getText(), "Сайт")) {
            bot.sendMsg(request, BotStringConstant.SITE_STRING);
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
