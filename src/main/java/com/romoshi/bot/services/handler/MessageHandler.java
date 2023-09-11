package com.romoshi.bot.services.handler;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.models.User;
import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.UserService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import com.romoshi.bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    final ProductService productService;
    final UserService userService;

    private final AdminService adminService;
    private final AdminUtil adminUtil;

    public static String pendingAction = null;
    public static String pendingUserId = null;

    ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();
    InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    public BotApiMethod<?> answerMessage(Message message) {
        String messageText = message.getText();
        String chatId = message.getChatId().toString();

        if(adminUtil.isAdmin(chatId)) {
            if(messageText.equals(CommandConstant.ADD_COMMAND)) {
                return adminService.addProduct(message);
            }
        }

        return switch (messageText) {
            case CommandConstant.START_COMMAND -> getStart(message);
            case CommandConstant.SHOW_SITE_COMMAND -> getSite(message);
            case CommandConstant.PRODUCTS_COMMAND -> sendProductList(message);
            default -> getDefault(message);
        };
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

    private SendMessage getStart(Message message) {
        String chatId = message.getChatId().toString();

        if(!userService.checkIfUserExists(chatId)) {
            User user = new User();
            user.setChatId(chatId);
            userService.saveUser(user);
        }

        SendMessage sendMessage = sendMsg(message, BotStringConstant.START_STRING);
        sendMessage.setReplyMarkup(replyKeyboardMaker.
                getReplyKeyboard(adminUtil.isAdmin(chatId)));

        return sendMessage;
    }

    private SendMessage getSite(Message message) {
        return sendMsg(message, BotStringConstant.SITE_STRING);
    }

    private BotApiMethod<?> sendProductList(Message message) {
        List<Product> products = productService.getAllProducts();

        SendMessage sendMessage = sendMsg(message, BotStringConstant.PRODUCT_LIST);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getProductsButtons(products));

        if (products.isEmpty()) {
            return sendMsg(message, BotStringConstant.HAVE_NOT_PRODUCTS);
        } else {
            return sendMessage;
        }
    }

    private SendMessage getDefault(Message message) {
        return sendMsg(message, BotStringConstant.DEFAULT_STRING);
    }

}
