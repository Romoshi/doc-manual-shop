package com.romoshi.bot.telegram;

import com.romoshi.bot.telegram.command.DefaultCommand;
import com.romoshi.bot.telegram.command.Handler;
import com.romoshi.bot.telegram.command.StartCommand;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;

    public TelegramBot(SetWebhook setWebhook, String botToken) {
        super(setWebhook, botToken);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Bot work!");
            return sendMessage;
        }
        return null;
    }
}
