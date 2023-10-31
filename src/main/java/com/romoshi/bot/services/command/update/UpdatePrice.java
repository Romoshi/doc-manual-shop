package com.romoshi.bot.services.command.update;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.session.UserContext;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@Slf4j
public class UpdatePrice implements UpdateProduct {

    private final ProductService productService;

    @Autowired
    public UpdatePrice(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public SendMessage update(Message message) {
        String chatId = message.getChatId().toString();

        try {
            UserContext userContext = MessageHandler.userContextHolder.getUserContext(chatId);

            long productId = extractProductId(userContext.getAction());
            Integer.parseInt(message.getText());

            Product product = productService.getProductById(productId);
            productService.updateProductPrice(product.getId(),
                    BigDecimal.valueOf(Integer.parseInt(message.getText())));

            return sendMsg(message, BotStringConstant.UPDATE_PRICE_MSG);
        } catch (NumberFormatException e) {
            return sendMsg(message, "Введите, пожалуйста, число.");
        } finally {
            MessageHandler.userContextHolder.clearActionUserContext(chatId);
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
