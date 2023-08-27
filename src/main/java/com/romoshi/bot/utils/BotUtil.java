package com.romoshi.bot.utils;

import com.romoshi.bot.telegram.command.DefaultCommand;
import com.romoshi.bot.telegram.command.Handler;
import com.romoshi.bot.telegram.command.StartCommand;

public class BotUtil {

    //TODO: Переделать под сообщения от бота.
    public void handlerInit(int request) {
        Handler handler1 = new StartCommand();
        Handler handler2 = new DefaultCommand();

        // Устанавливаем цепочку обработчиков
        handler1.setSuccessor(handler2);

        // Отправляем запрос на обработку
        handler1.handleRequest(request);
    }
}
