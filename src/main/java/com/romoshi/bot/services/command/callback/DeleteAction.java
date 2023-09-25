package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class DeleteAction implements Action {

    final ProductService productService;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        long productId = extractProductId(data);

        productService.deleteProduct(productId);

        return sendMsg(callbackQuery.getMessage(), BotStringConstant.DELETE_MESSAGE);
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_DELETE;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_DELETE + "_", "");
        return Long.parseLong(id);
    }
}
