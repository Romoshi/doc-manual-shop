package com.romoshi.bot.telegram.keyboards;

import com.romoshi.bot.models.Product;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getProductsButtons(List<Product> products) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Product product : products) {
            rowList.add(getButton(product.getName(), product.getId().toString()));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String id) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData("button_" + id);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    public InlineKeyboardMarkup getPayButton(int price, String id) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("Купить за: " + price + "₽", "price_" + id));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
