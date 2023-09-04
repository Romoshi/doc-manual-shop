package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${doctorID.admin}")
    public static String adminID;

    ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();
    public BotApiMethod<?> answerMessage(Update update) {
        String messageText = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        if(chatId.equals(adminID)) {

        } else {
            return switch (messageText) {
                case CommandConstant.START_COMMAND -> getStart(update);
                case CommandConstant.SHOW_SITE_COMMAND -> getSite(update);
                case CommandConstant.PRODUCTS_COMMAND -> sendProductList(update);
                default -> getDefault(update);
            };
        }

        return null;
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
            for (Product product : products) {
                String result = product.getImageUrl() + "\n"
                        + product.getDescription() + "\n"
                        + product.getPrice() + "₽";
                return sendMsg(update, result);
            }
        }

        return null;
    }

    private SendMessage getDefault(Update update) {
        return sendMsg(update, BotStringConstant.DEFAULT_STRING);
    }
}
