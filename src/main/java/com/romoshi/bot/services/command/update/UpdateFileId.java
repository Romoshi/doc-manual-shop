package com.romoshi.bot.services.command.update;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.file.FileDownloaderService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.services.handler.MessageHandler.pendingSetNull;
import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class UpdateFileId implements UpdateProduct {

    final ProductService productService;
    final FileDownloaderService fileDownloaderService;

    @Override
    public BotApiMethod<?> update(Message message, Product product) {
        fileDownloaderService.downloadTelegramFile(message.getDocument());
        productService.updateProductFileId(product.getId(), message.getDocument().getFileId());
        pendingSetNull();
        return sendMsg(message, BotStringConstant.UPDATE_FILE_ID_MSG);
    }

    @Override
    public String getUpdateName() {
        return ButtonConstant.BUTTON_UPDATE_FILE;
    }
}
