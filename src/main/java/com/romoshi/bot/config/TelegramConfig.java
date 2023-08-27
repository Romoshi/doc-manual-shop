package com.romoshi.bot.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TelegramConfig {

    @Value("${bot.webhookPath}")
    private String webhookPath;

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;
}
