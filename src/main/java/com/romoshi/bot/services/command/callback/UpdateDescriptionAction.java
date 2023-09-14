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
public class UpdateDescriptionAction implements Action {

    final ProductService productService;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        MessageHandler.pendingAction = ButtonConstant.BUTTON_UPDATE_DESCR;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId();

        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_DESCR_MSG_HANDLE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE_DESCR;
    }

    @Override
    public SendMessage update(Message message, Product product) {
        productService.updateProductDescription(product.getId(), message.getText());
        pendingSetNull();
        return sendMsg(message, BotStringConstant.UPDATE_DESCRIPTION_MSG);
    }

}
