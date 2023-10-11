package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@Slf4j
public class PriceAction implements Action {

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        String action = callbackQuery.getData();

        MessageHandler.userContextHolder.updateUserContext(chatId, action);

        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_PRICE_HANDLE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE_PRICE;
    }
}
