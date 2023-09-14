package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.services.handler.MessageHandler.pendingSetNull;
import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class UpdatePriceAction implements Action {
    final ProductService productService;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        MessageHandler.pendingAction = ButtonConstant.BUTTON_UPDATE_PRICE;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId();

        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_PRICE_MSG_HANDLE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE_PRICE;
    }

    @Override
    public SendMessage update(Message message, Product product) {
        try {
            Integer.parseInt(message.getText());
            productService.updateProductPrice(product.getId(), Integer.parseInt(message.getText()));
            pendingSetNull();

            return sendMsg(message, BotStringConstant.UPDATE_PRICE_MSG);
        } catch (NumberFormatException e) {
            return sendMsg(message, "Введите, пожалуйста, число.");
        }
    }
}
