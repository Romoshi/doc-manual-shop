package com.romoshi.bot.services.utils;

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
import java.math.RoundingMode;
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
        this.providerToken = providerToken;
        this.botToken = botToken;
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
        String requestBody = createJSON(link, chatId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        try{
            restTemplate.postForEntity(
                    sendInvoiceUrl,
                    requestEntity, Object.class);
        } catch (Exception ex) {
            log.error("Payment request error");
        }

    }

    private String createJSON(CreateInvoiceLink link, String chatId) {
        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMaker.getPayButton();

        BigDecimal price = BigDecimal.valueOf(link.getPrices().get(0).getAmount());
        int value = price.divide(BigDecimal.valueOf(100L), RoundingMode.HALF_UP).intValue();

        Map<String, Object> amount = new HashMap<>();
        amount.put("value", value);
        amount.put("currency", link.getCurrency());

        Map<String, Object> itemsObj = new HashMap<>();
        itemsObj.put("description", link.getTitle());
        itemsObj.put("quantity", 1);
        itemsObj.put("amount", amount);
        itemsObj.put("vat_code", 1);

        List<Object> items = new ArrayList<>();
        items.add(itemsObj);

        Map<String, Object> email = new HashMap<>();
        email.put("email", link.getNeedEmail());

        Map<String, Object> receiptObj = new HashMap<>();
        receiptObj.put("items", items);
        receiptObj.put("customer", email);

        Map<String, Object> receipt = new HashMap<>();
        receipt.put("receipt", receiptObj);

        Map<String, Object> requestObject = new HashMap<>();
        requestObject.put("chat_id", chatId);
        requestObject.put("title", link.getTitle());
        requestObject.put("description", link.getDescription());
        requestObject.put("payload", link.getPayload());
        requestObject.put("provider_token", link.getProviderToken());
        requestObject.put("currency", link.getCurrency());
        requestObject.put("prices", link.getPrices());
        requestObject.put("reply_markup", Map.of("inline_keyboard", keyboard));
        requestObject.put("need_email", true);
        requestObject.put("send_email_to_provider", true);
        requestObject.put("provider_data", receipt);

        return gson.toJson(requestObject);
    }
}
