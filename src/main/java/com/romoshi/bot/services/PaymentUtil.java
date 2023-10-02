package com.romoshi.bot.services;

import com.google.gson.Gson;
import com.romoshi.bot.entity.Product;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PaymentUtil {

    private final String providerToken;
    private final String botToken;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    public PaymentUtil(RestTemplate restTemplate,
                       @Value("${bot.token}") String botToken,
                       @Value("${payment.token}") String providerToken) {
        this.restTemplate = restTemplate;
        this.botToken = botToken;
        this.providerToken = providerToken;
        this.gson = new Gson();
        this.inlineKeyboardMaker = new InlineKeyboardMaker();
    }

    public void createUrl(Product product, String chatId) {
        List<LabeledPrice> prices = new ArrayList<>();
        prices.add(new LabeledPrice(product.getName(),
                product.getPrice().multiply(BigDecimal.valueOf(100L)).intValue()));

        CreateInvoiceLink link = new CreateInvoiceLink(product.getName(),
                product.getDescription(), product.getId().toString(),
                providerToken, "RUB", prices);

       sendInvoice(link, chatId);
    }

    private void sendInvoice(CreateInvoiceLink link, String chatId) {
        String sendInvoiceUrl = "https://api.telegram.org/bot" + botToken + "/sendInvoice";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMaker.getPayButton();


        Map<String, Object> requestObject = new HashMap<>();
        requestObject.put("chat_id", chatId);
        requestObject.put("title", link.getTitle());
        requestObject.put("description", link.getDescription());
        requestObject.put("payload", link.getPayload());
        requestObject.put("provider_token", link.getProviderToken());
        requestObject.put("currency", link.getCurrency());
        requestObject.put("prices", link.getPrices());
        requestObject.put("reply_markup", Map.of("inline_keyboard", keyboard));
        requestObject.put("protect_content", true);

        String requestBody = gson.toJson(requestObject);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.postForEntity(
                sendInvoiceUrl,
                requestEntity, Object.class);
    }
}
