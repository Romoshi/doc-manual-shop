package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.file.FileSenderService;
import com.romoshi.bot.services.file.FileService;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuyAction implements Action {

    final FileSenderService fileSenderService;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        String chatId = callbackQuery.getMessage().getChatId().toString();

        try {

            fileSenderService.sendFileToChat(chatId, product.getFileId());
            return sendMsg(callbackQuery.getMessage(), "kk");
        } catch (NullPointerException ex) {
            log.error("Файл не найден", ex);
        }

        return null;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON_PAY;
    }
}
