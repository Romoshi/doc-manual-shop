package com.romoshi.bot.services.command.update;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.session.UserContext;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class UpdateName implements UpdateProduct {

    private final ProductService productService;

    @Autowired
    public UpdateName(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public SendMessage update(Message message) {
        String chatId = message.getChatId().toString();

        UserContext userContext = MessageHandler.userContextHolder.getUserContext(chatId);

        long productId = extractProductId(userContext.getAction());

        Product product = productService.getProductById(productId);
        productService.updateProductName(product.getId(), message.getText());

        MessageHandler.userContextHolder.clearActionUserContext(chatId);

        return sendMsg(message, BotStringConstant.UPDATE_NAME_MSG);
    }

    @Override
    public String getUpdateName() {
        return ButtonConstant.BUTTON_UPDATE_NAME;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_UPDATE_NAME + "_", "");
        return Long.parseLong(id);
    }
}
