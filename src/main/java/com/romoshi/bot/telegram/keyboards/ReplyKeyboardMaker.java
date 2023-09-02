package com.romoshi.bot.telegram.keyboards;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;


@Component
public class ReplyKeyboardMaker {

    public ReplyKeyboardMarkup getInlineMessageButtons() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        KeyboardButton button1 = new KeyboardButton("Товары");
        KeyboardButton button2 = new KeyboardButton("Сайт");

        keyboardMarkup.setKeyboard(List.of((KeyboardRow) List.of(button1, button2)));

        return keyboardMarkup;
    }

}
