package com.romoshi.bot.telegram;

import com.romoshi.bot.services.CallbackQueryHandler;
import com.romoshi.bot.services.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@Slf4j
public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;

    private MessageHandler messageHandler;
    private CallbackQueryHandler callbackQueryHandler;

    public TelegramBot(SetWebhook setWebhook, String botToken, MessageHandler messageHandler,
                       CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook, botToken);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                return callbackQueryHandler.processCallbackQuery(callbackQuery);
            }

            if(update.hasMessage() && update.getMessage().hasText()) {
                return messageHandler.answerMessage(update);
            }
        } catch (Exception ex) {
            log.error("Update error", ex);
        }

        return null;
    }

    public static SendMessage sendMsg(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(text);
        return sendMessage;
    }
}
