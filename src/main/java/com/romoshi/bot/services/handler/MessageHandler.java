package com.romoshi.bot.services.handler;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.UserService;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    final ProductService productService;
    final UserService userService;

    private final CommandFactory commandFactory;

    private final AdminService adminService;
    private final AdminUtil adminUtil;

    public static String pendingAction = null;
    public static String pendingUserId = null;

    public BotApiMethod<?> answerMessage(Message message) {
        String messageText = message.getText();
        String chatId = message.getChatId().toString();

        if(adminUtil.isAdmin(chatId)) {
            if(messageText.equals(CommandConstant.ADD_COMMAND)) {
                return adminService.addProduct(message);
            }
        }

        Command command = commandFactory.createCommand(messageText);
        return command.execute(message);
    }

    public BotApiMethod<?> pendingAction(Message message) {
        List<Product> products = productService.getAllProducts();
        for(var product : products) {
            if (pendingAction != null &&
                    Objects.equals(message.getChatId().toString(), pendingUserId)) {

                if (pendingAction.equals(ButtonConstant.BUTTON_UPDATE_NAME + product.getId())) {
                    productService.updateProductName(product.getId(), message.getText());
                    pendingSetNull();
                    return sendMsg(message, BotStringConstant.UPDATE_NAME_MSG);
                } else if (pendingAction.equals(ButtonConstant.BUTTON_UPDATE_DESCR + product.getId())) {
                    productService.updateProductDescription(product.getId(), message.getText());
                    pendingSetNull();
                    return sendMsg(message, BotStringConstant.UPDATE_DESCRIPTION_MSG);
                } else if (pendingAction.equals(ButtonConstant.BUTTON_UPDATE_PRICE + product.getId())) {
                    try {
                        Integer.parseInt(message.getText());
                        productService.updateProductPrice(product.getId(),
                                Integer.parseInt(message.getText()));
                        pendingSetNull();
                        return sendMsg(message, BotStringConstant.UPDATE_PRICE_MSG);
                    } catch (NumberFormatException e) {
                        return sendMsg(message, "Введите, пожалуйста, число.");
                    }

                }
            }
        }

        return answerMessage(message);
    }

    private void pendingSetNull() {
        pendingAction = null;
        pendingUserId = null;
    }
}
