package com.romoshi.bot.services.command.update;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.services.handler.MessageHandler.pendingSetNull;
import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class UpdateDescription implements UpdateProduct {

    final ProductService productService;

    @Override
    public SendMessage update(Message message) {
        long productId = extractProductId(MessageHandler.pendingAction);

        Product product = productService.getProductById(productId);
        productService.updateProductDescription(product.getId(), message.getText());

        pendingSetNull();
        return sendMsg(message, BotStringConstant.UPDATE_DESCRIPTION_MSG);
    }

    @Override
    public String getUpdateName() {
        return ButtonConstant.BUTTON_UPDATE_DESCR;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_UPDATE_DESCR + "_", "");
        return Long.parseLong(id);
    }
}
