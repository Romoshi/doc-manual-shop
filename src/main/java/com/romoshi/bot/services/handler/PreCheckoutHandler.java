package com.romoshi.bot.services.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;

@Service
public class PreCheckoutHandler {

    @Value("${bot.token}")
    private String botToken;

    public BotApiMethod<?> processPreCheckOut(PreCheckoutQuery preCheckoutQuery) {
        AnswerPreCheckoutQuery answer = new AnswerPreCheckoutQuery();
        answer.setPreCheckoutQueryId(preCheckoutQuery.getId());
        answer.setOk(true);

        String apiUrl = "https://api.telegram.org/bot" + botToken + "/answerPreCheckoutQuery";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(apiUrl, answer, String.class);

        return null;
    }
}
