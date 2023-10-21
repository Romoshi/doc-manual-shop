package com.romoshi.bot.telegram;

import com.romoshi.bot.services.handler.UpdateHandler;
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

    private final UpdateHandler updateHandler;

    public TelegramBot(SetWebhook setWebhook, String botToken, UpdateHandler updateHandler) {
        super(setWebhook, botToken);
        this.updateHandler = updateHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return updateHandler.updateProcess(update);
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
