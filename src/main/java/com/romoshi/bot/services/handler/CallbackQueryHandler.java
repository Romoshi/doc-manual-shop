package com.romoshi.bot.services.handler;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private final InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    private final AdminUtil adminUtil;
    final ProductService productService;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        List<Product> products = productService.getAllProducts();

        for(Product product : products) {
            if((ButtonConstant.BUTTON + product.getId()).equals(data)) {
                return firstCallback(callbackQuery, product);
            } else if((ButtonConstant.BUTTON_UPDATE + product.getId()).equals(data)) {
                return generalUpdateCallback(callbackQuery, product);
            } else if((ButtonConstant.BUTTON_DELETE + product.getId()).equals(data)) {
                return deleteCallback(callbackQuery, product);
            } else if((ButtonConstant.BUTTON_UPDATE_NAME + product.getId()).equals(data)) {
                return updateNameAction(callbackQuery,
                        ButtonConstant.BUTTON_UPDATE_NAME + product.getId());
            } else if((ButtonConstant.BUTTON_UPDATE_DESCR + product.getId()).equals(data)) {
                return updateDescrAction(callbackQuery,
                        ButtonConstant.BUTTON_UPDATE_DESCR + product.getId());
            } else if((ButtonConstant.BUTTON_UPDATE_PRICE + product.getId()).equals(data)) {
                return updatePriceAction(callbackQuery,
                        ButtonConstant.BUTTON_UPDATE_PRICE + product.getId());
            }
        }

        return null;
    }

    private BotApiMethod<?> firstCallback(CallbackQuery callbackQuery, Product product) {
        String chatId = callbackQuery.getMessage().getChatId().toString();

        SendMessage sendMessage = sendMsg(callbackQuery.getMessage(),
                "*" + product.getName() + "*" + "\n" + product.getDescription());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getPayButton(product.getPrice(),
                product.getId().toString(), adminUtil.isAdmin(chatId)));

        return sendMessage;
    }

    private BotApiMethod<?> generalUpdateCallback(CallbackQuery callbackQuery, Product product) {
        SendMessage sendMessageUpdate = sendMsg(callbackQuery.getMessage(),
                BotStringConstant.UPDATE_MESSAGE);
        sendMessageUpdate.setReplyMarkup(inlineKeyboardMaker.
                getUpdateButton(product.getId().toString()));

        return sendMessageUpdate;
    }

    private BotApiMethod<?> updateNameAction(CallbackQuery callbackQuery, String button) {
        MessageHandler.pendingAction = button;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId().toString();
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_NAME_MSG_HANDLE);
    }

    private BotApiMethod<?> updateDescrAction(CallbackQuery callbackQuery, String button) {
        MessageHandler.pendingAction = button;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId().toString();
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_DESCR_MSG_HANDLE);
    }

    private BotApiMethod<?> updatePriceAction(CallbackQuery callbackQuery, String button) {
        MessageHandler.pendingAction = button;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId().toString();
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_PRICE_MSG_HANDLE);
    }

    private BotApiMethod<?> deleteCallback(CallbackQuery callbackQuery, Product product) {
        productService.deleteProduct(product.getId());
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.DELETE_MESSAGE);
    }
}
