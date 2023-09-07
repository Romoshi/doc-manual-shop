package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import com.romoshi.bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    final ProductService productService;
    private final AdminUtil adminUtil;

    @Value("${developId}")
    private String adminID;
    public boolean isAdmin(String chatId) {
        return chatId.equals(adminID);
    }

    ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();
    InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    public BotApiMethod<?> answerMessage(Update update) {
        String messageText = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        if(isAdmin(chatId)) {
            if(messageText.equals(CommandConstant.ADD_COMMAND)) {
                return adminUtil.addProduct(update);
            }
        }

        return switch (messageText) {
            case CommandConstant.START_COMMAND -> getStart(update);
            case CommandConstant.SHOW_SITE_COMMAND -> getSite(update);
            case CommandConstant.PRODUCTS_COMMAND -> sendProductList(update);
            default -> getDefault(update);
        };
    }

    private SendMessage getStart(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        SendMessage sendMessage = sendMsg(update, BotStringConstant.START_STRING);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getReplyKeyboard(isAdmin(chatId)));

        return sendMessage;
    }

    private SendMessage getSite(Update update) {
        return sendMsg(update, BotStringConstant.SITE_STRING);
    }

    private BotApiMethod<?> sendProductList(Update update) {
        List<Product> products = productService.getAllProducts();

        SendMessage sendMessage = sendMsg(update, BotStringConstant.PRODUCT_LIST);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getProductsButtons(products));

        if (products.isEmpty()) {
            return sendMsg(update, BotStringConstant.HAVE_NOT_PRODUCTS);
        } else {
            return sendMessage;
        }
    }

    private SendMessage getDefault(Update update) {
        return sendMsg(update, BotStringConstant.DEFAULT_STRING);
    }

}
