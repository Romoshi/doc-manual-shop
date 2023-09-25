package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.file.FileSenderService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuyAction implements Action {

    private final ProductService productService;
    final FileSenderService fileSenderService;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        String data = callbackQuery.getData();

        long productId = extractProductId(data);

        Product product = productService.getProductById(productId);

        try {

            fileSenderService.sendFileToChat(chatId, product.getFileId());
            return sendMsg(callbackQuery.getMessage(), BotStringConstant.SALE_STRING);
        } catch (NullPointerException ex) {
            log.error("Файл не найден", ex);
        }

        return null;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_PAY;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_PAY + "_", "");
        return Long.parseLong(id);
    }
}
