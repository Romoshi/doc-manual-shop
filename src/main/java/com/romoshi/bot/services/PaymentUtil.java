package com.romoshi.bot.services;

import com.google.gson.Gson;
import com.romoshi.bot.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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







        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Купить за " + link.getPrices() + " " + link.getCurrency());
        button.setPay(true);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);





        Map<String, Object> requestObject = new HashMap<>();
        requestObject.put("chat_id", chatId);
        requestObject.put("title", link.getTitle());
        requestObject.put("description", link.getDescription());
        requestObject.put("payload", link.getPayload());
        requestObject.put("provider_token", link.getProviderToken());
        requestObject.put("currency", link.getCurrency());
        requestObject.put("prices", link.getPrices());
        requestObject.put("reply_markup", inlineKeyboardMarkup);
        requestObject.put("protect_content", true);

        String requestBody = gson.toJson(requestObject);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

       // ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(
                sendInvoiceUrl,
                requestEntity, Object.class);

//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            return responseEntity.getBody();
//        } else {
//            log.error("Ошибка при создании счета");
//        }
//
//        return null;
    }
}
