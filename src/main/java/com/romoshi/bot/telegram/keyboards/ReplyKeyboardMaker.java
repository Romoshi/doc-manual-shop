package com.romoshi.bot.telegram.keyboards;


import com.romoshi.bot.telegram.constant.CommandConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Component
public class ReplyKeyboardMaker {

    public ReplyKeyboardMarkup getReplyKeyboard(boolean isAdmin) {
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton(CommandConstant.PRODUCTS_COMMAND));
        if(isAdmin) row.add(new KeyboardButton(CommandConstant.ADD_COMMAND));
        row.add(new KeyboardButton(CommandConstant.SHOW_SITE_COMMAND));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
}
