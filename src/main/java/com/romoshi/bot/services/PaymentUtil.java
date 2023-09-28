package com.romoshi.bot.services;

import com.google.gson.Gson;
import com.romoshi.bot.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class PaymentUtil {

    private final String providerToken;
    private final String botToken;
    private final RestTemplate restTemplate;
    private final Gson gson;

    public PaymentUtil(RestTemplate restTemplate,
                       @Value("${bot.token}") String botToken,
                       @Value("${payment.token}") String providerToken) {
        this.restTemplate = restTemplate;
        this.botToken = botToken;
        this.providerToken = providerToken;
        this.gson = new Gson();
    }

    public String createUrl(Product product, String chatId) {
        List<LabeledPrice> prices = new ArrayList<>();
        prices.add(new LabeledPrice(product.getName(),
                product.getPrice().multiply(BigDecimal.valueOf(100L)).intValue()));

        CreateInvoiceLink link = new CreateInvoiceLink(product.getName(),
                product.getDescription(), product.getId().toString(),
                providerToken, "RUB", prices);

        return sendInvoice(link, chatId);
    }

    private String sendInvoice(CreateInvoiceLink link, String chatId) {
        String sendInvoiceUrl = "https://api.telegram.org/bot" + botToken + "/sendInvoice";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestObject = new HashMap<>();
        requestObject.put("chat_id", chatId);
        requestObject.put("title", link.getTitle());
        requestObject.put("description", link.getDescription());
        requestObject.put("payload", link.getPayload());
        requestObject.put("provider_token", link.getProviderToken());
        requestObject.put("currency", link.getCurrency());
        requestObject.put("prices", link.getPrices());

        String requestBody = gson.toJson(requestObject);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                sendInvoiceUrl,
                requestEntity,
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            log.error("Ошибка при создании счета");
        }

        return null;
    }
}
