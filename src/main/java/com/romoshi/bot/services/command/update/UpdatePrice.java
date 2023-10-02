package com.romoshi.bot.services.command.update;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;

import static com.romoshi.bot.services.handler.MessageHandler.pendingSetNull;
import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class UpdatePrice implements UpdateProduct {

    private final ProductService productService;

    @Autowired
    public UpdatePrice(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public SendMessage update(Message message) {
        try {

            long productId = extractProductId(MessageHandler.pendingAction);
            Integer.parseInt(message.getText());

            Product product = productService.getProductById(productId);
            productService.updateProductPrice(product.getId(),
                    BigDecimal.valueOf(Integer.parseInt(message.getText())));

            pendingSetNull();

            return sendMsg(message, BotStringConstant.UPDATE_PRICE_MSG);
        } catch (NumberFormatException e) {
            return sendMsg(message, "Введите, пожалуйста, число.");
        }
    }

    @Override
    public String getUpdateName() {
        return ButtonConstant.BUTTON_UPDATE_PRICE;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_UPDATE_PRICE + "_", "");
        return Long.parseLong(id);
    }
}
