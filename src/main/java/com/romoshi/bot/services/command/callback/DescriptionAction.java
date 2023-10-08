package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class DescriptionAction implements Action {

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        MessageHandler.pendingAction = callbackQuery.getData();

        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_DESCR_HANDLE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE_DESCR;
    }
}
