package com.romoshi.bot.services.command.callback;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.keyboards.InlineKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class ProductCallbackAction implements Action {

    private final InlineKeyboardMaker inlineKeyboardMaker = new InlineKeyboardMaker();

    private final AdminUtil adminUtil;

    @Override
    public BotApiMethod<?> execute(CallbackQuery callbackQuery, Product product) {
        String chatId = callbackQuery.getMessage().getChatId().toString();

        SendMessage sendMessage = sendMsg(callbackQuery.getMessage(),
                "*" + product.getName() + "*" + "\n" + product.getDescription());

        sendMessage.setReplyMarkup(inlineKeyboardMaker.getPayButton(product.getPrice(),
                product.getId().toString(), adminUtil.isAdmin(chatId)));

        return sendMessage;
    }

    @Override
    public String getActionName() {
        return ButtonConstant.BUTTON;
    }
}
