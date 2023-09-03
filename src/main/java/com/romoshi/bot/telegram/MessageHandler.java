package com.romoshi.bot.telegram;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

public class MessageHandler {
    public static BotApiMethod<?> answerMessage(Update update) {
        String messageText = update.getMessage().getText();

        if(messageText.equals(CommandConstant.START_COMMAND)) {
            return getStart(update);
        } else if(messageText.equals(CommandConstant.SHOW_SITE_COMMAND)) {
            return getSite(update);
        }else if(messageText.equals(CommandConstant.PRODUCTS_COMMAND)) {
            return getProducts(update);
        }else {
            return getDefault(update);
        }
    }

    public static SendMessage getStart(Update update) {
        return sendMsg(update, BotStringConstant.START_STRING);
    }

    public static SendMessage getSite(Update update) {
        return sendMsg(update, BotStringConstant.SITE_STRING);
    }

    public static SendMessage getProducts(Update update) {
        return sendMsg(update, BotStringConstant.SALE_STRING);
    }

    public static SendMessage getDefault(Update update) {
        return sendMsg(update, BotStringConstant.DEFAULT_STRING);
    }
}
