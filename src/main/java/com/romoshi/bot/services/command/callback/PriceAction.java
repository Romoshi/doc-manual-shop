package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.session.UserContext;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@Slf4j
public class PriceAction implements Action {

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        //log.info(MessageHandler.userContextHolder.getUserContext().getCurrentState());

        UserContext userContext = MessageHandler.userContextHolder.getUserContext();

        userContext.setUserId(callbackQuery.getMessage().getChatId().toString());
        userContext.setCurrentState(callbackQuery.getData());

        MessageHandler.userContextHolder.setUserContext(userContext);

       //MessageHandler.pendingAction = callbackQuery.getData();


        log.info(MessageHandler.userContextHolder.getUserContext().getCurrentState());
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_PRICE_HANDLE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE_PRICE;
    }
}
