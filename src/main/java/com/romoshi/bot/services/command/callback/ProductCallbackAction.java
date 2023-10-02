package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.PaymentUtil;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class ProductCallbackAction implements Action {


    private final ProductService productService;
    private final PaymentUtil paymentUtil;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final AdminUtil adminUtil;

    @Autowired
    public ProductCallbackAction(ProductService productService, PaymentUtil paymentUtil,
                                 AdminUtil adminUtil) {
        this.productService = productService;
        this.paymentUtil = paymentUtil;
        this.adminUtil = adminUtil;
        this.inlineKeyboardMaker = new InlineKeyboardMaker();
    }

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery) {
        SendMessage sendMessage = new SendMessage();
        String data = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();

        long productId = extractProductId(data);
        Product product = productService.getProductById(productId);

        paymentUtil.createUrl(product, chatId);

        if (adminUtil.isAdmin(chatId)) {
            sendMessage = sendMsg(callbackQuery.getMessage(), "Панель администратора: ");
            sendMessage.setReplyMarkup(inlineKeyboardMaker.getAdminButton(product.getId().toString()));
        }

        return sendMessage;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON;
    }

    private long extractProductId(String data) {
        String id = data.replace(ButtonConstant.BUTTON + "_", "");
        return Long.parseLong(id);
    }
}
