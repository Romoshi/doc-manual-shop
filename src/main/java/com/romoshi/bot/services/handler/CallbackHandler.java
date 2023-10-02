package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.command.callback.Action;
import com.romoshi.bot.services.command.callback.ActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class CallbackHandler {

    private final ActionFactory actionFactory;

    @Autowired
    public CallbackHandler(ActionFactory actionFactory) {
        this.actionFactory = actionFactory;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("_");
        Action action = actionFactory.createAction(data[0]);

        return action.execute(callbackQuery);
    }
}
