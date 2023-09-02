package com.romoshi.bot.controller;

import com.romoshi.bot.telegram.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
@Slf4j
public class WebhookController {
    public final TelegramBot telegramBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        try {
           return telegramBot.onWebhookUpdateReceived(update);
        } catch (Exception ex) {
            log.error("Webhook exception", ex);
        }
        return null;
    }
}
