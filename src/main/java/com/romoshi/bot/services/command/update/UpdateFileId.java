package com.romoshi.bot.services.command.update;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.file.FileDownloaderService;
import com.romoshi.bot.services.handler.MessageHandler;
import com.romoshi.bot.session.UserContext;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class UpdateFileId implements UpdateProduct {

    private final ProductService productService;
    private final FileDownloaderService fileDownloaderService;

    @Autowired
    public UpdateFileId(ProductService productService,
                        FileDownloaderService fileDownloaderService) {
        this.productService = productService;
        this.fileDownloaderService =  fileDownloaderService;
    }

    @Override
    public BotApiMethod<?> update(Message message) {
        String chatId = message.getChatId().toString();

        fileDownloaderService.downloadTelegramFile(message.getDocument());

        UserContext userContext = MessageHandler.userContextHolder.getUserContext(chatId);

        long productId = extractProductId(userContext.getAction());

        Product product = productService.getProductById(productId);
        productService.updateProductFileId(product.getId(),
                message.getDocument().getFileId());

        MessageHandler.userContextHolder.clearActionUserContext(chatId);

        return sendMsg(message, BotStringConstant.UPDATE_FILE_ID_MSG);
    }

    @Override
    public String getUpdateName() {
        return ButtonConstant.BUTTON_UPDATE_FILE;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON_UPDATE_FILE + "_", "");
        return Long.parseLong(id);
    }
}
