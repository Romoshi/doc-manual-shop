package com.romoshi.bot.telegram;

import com.romoshi.bot.config.TelegramConfig;
import com.romoshi.bot.telegram.command.DefaultCommand;
import com.romoshi.bot.telegram.command.Handler;
import com.romoshi.bot.telegram.command.StartCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@Slf4j
public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;
    private static TelegramConfig telegramConfig;
    public static final TelegramBot bot = new TelegramBot(SetWebhook.builder()
                                                       .url(telegramConfig
                                                               .getWebhookPath()).build(),
                                            telegramConfig.getBotToken());

    public TelegramBot(SetWebhook setWebhook, String botToken) {
        super(setWebhook, botToken);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage();
                parseMessage(message);
            }
        } catch (Exception e) {
            log.error("Update problems", e);
        }

        return null;
    }

    private void parseMessage(Message message) {

    }

    public void sendMsg(Message message, String s) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(s);

            execute(sendMessage);
        } catch (TelegramApiException ex) {
            log.error("Send Message trouble", ex);
        }
    }

    //TODO: Переделать под сообщения от бота.
    public void handlerInit(Message request) {
        Handler handler1 = new StartCommand();
        Handler handler2 = new DefaultCommand();

        // Устанавливаем цепочку обработчиков
        handler1.setSuccessor(handler2);

        // Отправляем запрос на обработку
        handler1.handleRequest(request);
    }
}
