package com.romoshi.bot.telegram;

import com.romoshi.bot.services.handler.CallbackHandler;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.services.handler.PreCheckoutHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@Slf4j
public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;
    private final PreCheckoutHandler preCheckoutHandler;

    public TelegramBot(SetWebhook setWebhook, String botToken, MessageHandler messageHandler,
                       CallbackHandler callbackHandler, PreCheckoutHandler preCheckoutHandler) {
        super(setWebhook, botToken);
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
        this.preCheckoutHandler = preCheckoutHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                return callbackHandler.processCallbackQuery(update.getCallbackQuery());
            } else if(update.hasMessage()) {
                return messageHandler.processMessage(update.getMessage());
            } else if(update.hasPreCheckoutQuery()) {
                return preCheckoutHandler.processPreCheckOut(update.getPreCheckoutQuery());
            }
        } catch (Exception ex) {
            log.error("Update error", ex);
        }

        return sendMsg(update.getMessage(), BotStringConstant.DEFAULT_STRING);
    }

    public static SendMessage sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);

        return sendMessage;
    }
}
