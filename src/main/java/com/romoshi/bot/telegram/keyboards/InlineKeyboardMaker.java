package com.romoshi.bot.telegram.keyboards;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.telegram.constant.BotStringConstant;
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
            rowList.add(getButton(product.getName(), "_" + product.getId().toString()));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String id) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData("button" + id);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    public InlineKeyboardMarkup getPayButton(int price, String id, boolean isAdmin) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("Купить за: " + price + "₽", "Pay"  + "_" + id));

        if(isAdmin) {
            rowList.add(getButton(BotStringConstant.UPDATE_GENERAL_INLINE, "Update"  + "_" + id));
            rowList.add(getButton(BotStringConstant.DELETE_INLINE, "Delete"  + "_" + id));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getUpdateButton(String id) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(BotStringConstant.UPDATE_FILE_INLINE, "UpdateFile"  + "_" + id));
        rowList.add(getButton(BotStringConstant.UPDATE_NAME_INLINE, "UpdateName"  + "_" + id));
        rowList.add(getButton(BotStringConstant.UPDATE_DESCRIPTION_INLINE, "UpdateDescription"  + "_" + id));
        rowList.add(getButton(BotStringConstant.UPDATE_PRICE_INLINE, "UpdatePrice"  + "_" + id));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
