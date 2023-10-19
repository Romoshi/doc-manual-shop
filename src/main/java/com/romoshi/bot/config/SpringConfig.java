package com.romoshi.bot.config;

import com.romoshi.bot.services.handler.CallbackHandler;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.services.handler.PaymentHandler;
import com.romoshi.bot.services.handler.PreCheckoutHandler;
import com.romoshi.bot.telegram.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public TelegramBot springWebhookBot(SetWebhook setWebhook,
                                        MessageHandler messageHandler,
                                        CallbackHandler callbackHandler,
                                        PreCheckoutHandler preCheckOutHandler,
                                        PaymentHandler paymentHandler) {

        TelegramBot bot = new TelegramBot(setWebhook, telegramConfig.getBotToken(),
                                        messageHandler,
                                        callbackHandler,
                                        preCheckOutHandler,
                                        paymentHandler);

        bot.setBotUsername(telegramConfig.getBotUsername());
        bot.setBotPath(telegramConfig.getWebhookPath());

        return bot;
    }
}
