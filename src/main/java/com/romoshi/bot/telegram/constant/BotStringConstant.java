package com.romoshi.bot.telegram.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BotStringConstant {

    public static final String START_STRING = "Привет, друг \uD83D\uDC4B \nЧто тебя интересует?";
    public static final String SALE_STRING = "Товар куплен!";
    public static final String ADD_STRING = "Товар добавлен!";
    public static final String PRODUCT_LIST = "Список товаров:";
    public static final String HAVE_NOT_PRODUCTS = "В магазине пока нет товаров.";
    public static final String SITE_STRING = "https://doctor-dyorina.tilda.ws/";
    public static final String DEFAULT_STRING = "Извини, к сожалению я не знаю, что с этим делать \uD83E\uDEE0";

    //Default info product button.
    public static final String DEFAULT_PRODUCT_NAME = "Название товара";
    public static final String DEFAULT_DESCRIPTION = "Описание товара.";
    public static final int DEFAULT_PRODUCT_PRICE = 100;

    //Update, delete button
    public static final String UPDATE_MESSAGE = "Что хотите изменить?";
    public static final String UPDATE_GENERAL_INLINE = "Изменить товар.";
    public static final String UPDATE_NAME_INLINE = "Изменить название товара.";
    public static final String UPDATE_DESCRIPTION_INLINE = "Изменить описание.";
    public static final String UPDATE_PRICE_INLINE = "Изменить стоймость.";
    public static final String DELETE_INLINE = "Удалить товар.";

}
