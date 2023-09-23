package com.romoshi.bot.telegram;

import com.romoshi.bot.services.handler.CallbackHandler;
import com.romoshi.bot.services.handler.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@Slf4j
public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;

    private MessageHandler messageHandler;
    private CallbackHandler callbackHandler;

    public TelegramBot(SetWebhook setWebhook, String botToken, MessageHandler messageHandler,
                       CallbackHandler callbackHandler) {
        super(setWebhook, botToken);
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                return callbackHandler.processCallbackQuery(callbackQuery);
            } else if(update.hasMessage()) {
                return messageHandler.pendingAction(update.getMessage());
            }
        } catch (Exception ex) {
            log.error("Update error", ex);
        }

        return null;
    }

    public static SendMessage sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);

        return sendMessage;
    }
}
