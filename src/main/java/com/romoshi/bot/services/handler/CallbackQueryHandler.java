package com.romoshi.bot.services.handler;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

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
            String button = "button_" + product.getId();
            String buttonUpdate = "button_update_" + product.getId();
            String buttonDelete = "button_delete_" + product.getId();

            if(button.equals(data)) {
                return firstCallback(callbackQuery, product);
            } else if(buttonUpdate.equals(data)) {
                return generalUpdateCallback(callbackQuery, product);
            } else if(buttonDelete.equals(data)) {
                return deleteCallback(callbackQuery, product);
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

    public BotApiMethod<?> deleteCallback(CallbackQuery callbackQuery, Product product) {
        productService.deleteProduct(product.getId());
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.DELETE_MESSAGE);
    }
}
