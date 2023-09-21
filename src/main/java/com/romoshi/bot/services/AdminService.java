package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.models.User;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class AdminService {

    final ProductService productService;
    final UserService userService;

    public SendMessage addProduct(Message message) {
        Product defaultProduct = new Product();
        defaultProduct.setFileId(BotStringConstant.DEFAULT_PRODUCT_FILE_ID);
        defaultProduct.setName(BotStringConstant.DEFAULT_PRODUCT_NAME);
        defaultProduct.setDescription(BotStringConstant.DEFAULT_DESCRIPTION);
        defaultProduct.setPrice(BotStringConstant.DEFAULT_PRODUCT_PRICE);

        productService.saveProduct(defaultProduct);
        sendNotification();

        return sendMsg(message, BotStringConstant.ADD_STRING);
    }

    public void sendNotification() {
        List<User> users = userService.getAllUsers();

        for (User user : users) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText(BotStringConstant.NOTIFICATION_STRING);
        }
    }
}
