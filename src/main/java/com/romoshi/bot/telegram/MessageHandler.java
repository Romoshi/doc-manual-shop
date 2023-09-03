package com.romoshi.bot.telegram;

import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();
    public BotApiMethod<?> answerMessage(Update update) {
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

    private SendMessage getStart(Update update) {
        SendMessage sendMessage = sendMsg(update, BotStringConstant.START_STRING);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainKeyboard());
        return sendMessage;
    }

    public SendMessage getSite(Update update) {
        return sendMsg(update, BotStringConstant.SITE_STRING);
    }

    public SendMessage getProducts(Update update) {
        return sendMsg(update, BotStringConstant.SALE_STRING);
    }

    public SendMessage getDefault(Update update) {
        return sendMsg(update, BotStringConstant.DEFAULT_STRING);
    }
}
