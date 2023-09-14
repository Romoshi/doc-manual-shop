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

import java.util.List;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class CallbackHandler {
    private final InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    private final AdminUtil adminUtil;
    final ProductService productService;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        List<Product> products = productService.getAllProducts();


        for(Product product : products) {








//            if(equalsButton(getButtonCallback(ButtonConstant.BUTTON, product), data)) {
//                return firstCallback(callbackQuery, product);
//            } else if(equalsButton(getButtonCallback(ButtonConstant.BUTTON_UPDATE, product), data)) {
//                return generalUpdateCallback(callbackQuery, product);
//            } else if(equalsButton(getButtonCallback(ButtonConstant.BUTTON_DELETE, product), data)) {
//                return deleteCallback(callbackQuery, product);
//            } else if(equalsButton(getButtonCallback(ButtonConstant.BUTTON_UPDATE_NAME, product), data)) {
//                return updateAction(callbackQuery,
//                        getButtonCallback(ButtonConstant.BUTTON_UPDATE_NAME, product),
//                        product);
//            } else if(equalsButton(getButtonCallback(ButtonConstant.BUTTON_UPDATE_DESCR, product), data)) {
//                return updateAction(callbackQuery,
//                        getButtonCallback(ButtonConstant.BUTTON_UPDATE_DESCR, product),
//                        product);
//            } else if(equalsButton(getButtonCallback(ButtonConstant.BUTTON_UPDATE_PRICE, product), data)) {
//                return updateAction(callbackQuery,
//                        getButtonCallback(ButtonConstant.BUTTON_UPDATE_PRICE, product),
//                        product);
//            }
        }

        return null;
    }

    public BotApiMethod<?> firstCallback(CallbackQuery callbackQuery, Product product) {
        String chatId = callbackQuery.getMessage().getChatId().toString();

        SendMessage sendMessage = sendMsg(callbackQuery.getMessage(),
                "*" + product.getName() + "*" + "\n" + product.getDescription());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getPayButton(product.getPrice(),
                product.getId().toString(), adminUtil.isAdmin(chatId)));

        return sendMessage;
    }

    public BotApiMethod<?> generalUpdateCallback(CallbackQuery callbackQuery, Product product) {
        SendMessage sendMessageUpdate = sendMsg(callbackQuery.getMessage(),
                BotStringConstant.UPDATE_MESSAGE);
        sendMessageUpdate.setReplyMarkup(inlineKeyboardMaker.
                getUpdateButton(product.getId().toString()));

        return sendMessageUpdate;
    }

    public BotApiMethod<?> updateAction(CallbackQuery callbackQuery, String button, Product product) {
        MessageHandler.pendingAction = button;
        MessageHandler.pendingUserId = callbackQuery.getMessage().getChatId().toString();

        if(button.equals(ButtonConstant.BUTTON_UPDATE_NAME + product.getId()))
            return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_NAME_MSG_HANDLE);

        if(button.equals(ButtonConstant.BUTTON_UPDATE_DESCR + product.getId()))
            return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_DESCR_MSG_HANDLE);

        if(button.equals(ButtonConstant.BUTTON_UPDATE_PRICE + product.getId()))
            return sendMsg(callbackQuery.getMessage(), BotStringConstant.UPDATE_PRICE_MSG_HANDLE);

        return null;
    }

    public BotApiMethod<?> deleteCallback(CallbackQuery callbackQuery, Product product) {
        productService.deleteProduct(product.getId());
        return sendMsg(callbackQuery.getMessage(), BotStringConstant.DELETE_MESSAGE);
    }

    //Сравнение команды текущего нажатия с сохранёнными
    public boolean equalsButton(String button, String data) {
        return (button.equals(data));
    }

    public String getButtonCallback(String button, Product product) {
        return button + product.getId();
    }
}
