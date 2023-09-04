package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    final ProductService productService;

    ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();
    public BotApiMethod<?> answerMessage(Update update) {
        String messageText = update.getMessage().getText();

        if(messageText.equals(CommandConstant.START_COMMAND)) {
            return getStart(update);
        } else if(messageText.equals(CommandConstant.SHOW_SITE_COMMAND)) {
            return getSite(update);
        }else if(messageText.equals(CommandConstant.PRODUCTS_COMMAND)) {
            return sendProductList(update);
        }else {
            return getDefault(update);
        }
    }

    private SendMessage getStart(Update update) {
        SendMessage sendMessage = sendMsg(update, BotStringConstant.START_STRING);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainKeyboard());
        return sendMessage;
    }

    private SendMessage getSite(Update update) {
        return sendMsg(update, BotStringConstant.SITE_STRING);
    }

    private SendMessage sendProductList(Update update) {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            return sendMsg(update, BotStringConstant.HAVE_NOT_PRODUCTS);
        } else {
            StringBuilder productList = new StringBuilder();
            for (Product product : products) {
                productList.append(product.getDescription()).append("\n").append(product.getPrice()).append("\n");
            }
            return sendMsg(update, productList.toString());
        }
    }

    private SendMessage getDefault(Update update) {
        return sendMsg(update, BotStringConstant.DEFAULT_STRING);
    }
}
