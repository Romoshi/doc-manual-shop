package com.romoshi.bot.services.command.message;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class ProductsCommand implements Command {

    private final ProductService productService;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    @Autowired
    public ProductsCommand(ProductService productService) {
        this.productService = productService;
        this.inlineKeyboardMaker = new InlineKeyboardMaker();
    }
    @Override
    public BotApiMethod<?> execute(Message message) {
        List<Product> products = productService.getAllProducts();

        SendMessage sendMessage = sendMsg(message, BotStringConstant.PRODUCT_LIST);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getProductsButtons(products));

        if (products.isEmpty()) {
            return sendMsg(message, BotStringConstant.HAVE_NOT_PRODUCTS);
        } else {
            return sendMessage;
        }
    }

    @Override
    public String getCommandName() {
        return CommandConstant.PRODUCTS_COMMAND;
    }
}
