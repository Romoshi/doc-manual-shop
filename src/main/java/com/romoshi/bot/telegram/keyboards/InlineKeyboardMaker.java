package com.romoshi.bot.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();
        List<InlineKeyboardButton> inlineRow = new ArrayList<>();

        InlineKeyboardButton payment = new InlineKeyboardButton();
        payment.setText("Купить \uD83D\uDCB0");
        //TODO: Сделать оплату!
        payment.setCallbackData("ОПЛАТА");

        inlineRow.add(payment);
        inlineRows.add(inlineRow);

        inlineKeyboardMarkup.setKeyboard(inlineRows);

        return inlineKeyboardMarkup;
    }
}
