package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class UpdateAction implements Action {

    private final InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        SendMessage sendMessageUpdate = sendMsg(callbackQuery.getMessage(),
                BotStringConstant.UPDATE_MESSAGE);
        sendMessageUpdate.setReplyMarkup(inlineKeyboardMaker.
                getUpdateButton(product.getId().toString()));

        return sendMessageUpdate;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE;
    }

    @Override
    public BotApiMethod<?> update(Message message, Product product) {
        return null;
    }
}
