package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class UpdateAction implements Action {

    private final ProductService productService;
    private final InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();

        long productId = extractProductId(data);

        Product product = productService.getProductById(productId);

        SendMessage sendMessageUpdate = sendMsg(callbackQuery.getMessage(),
                BotStringConstant.UPDATE_MESSAGE);
        sendMessageUpdate.setReplyMarkup(inlineKeyboardMaker.
                getUpdateButton(product.getId().toString()));

        return sendMessageUpdate;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_UPDATE;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_UPDATE + "_", "");
        return Long.parseLong(id);
    }
}
