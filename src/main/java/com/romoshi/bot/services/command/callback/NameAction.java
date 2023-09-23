package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class NameAction implements Action {

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        MessageHandler.pendingAction = ButtonConstant.BUTTON_UPDATE_NAME;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId();

        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_NAME_HANDLE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE_NAME;
    }
}
