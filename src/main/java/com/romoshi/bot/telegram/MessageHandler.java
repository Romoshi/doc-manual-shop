package com.romoshi.bot.telegram;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

public class MessageHandler {
    public BotApiMethod<?> answerMessage(Message message) {
        return null;
    }

    public static SendMessage getStart(Update update) {
        return sendMsg(update, BotStringConstant.START_STRING);
    }

    public static SendMessage getSite(Update update) {
        return sendMsg(update, BotStringConstant.SITE_STRING);
    }

    public static SendMessage getDefault(Update update) {
        return sendMsg(update, BotStringConstant.DEFAULT_STRING);
    }
}
