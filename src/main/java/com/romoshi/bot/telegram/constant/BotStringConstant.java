package com.romoshi.bot.telegram.constant;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class BotStringConstant {

    public static final String START_STRING = "Привет, друг \uD83D\uDC4B \nЧто тебя интересует?";
    public static final String ADD_STRING = "Товар добавлен!";
    public static final String SALE_STRING = "Товар куплен!";
    public static final String PRODUCT_LIST = "Список товаров:";
    public static final String HAVE_NOT_PRODUCTS = "В магазине пока нет товаров.";
    public static final String SITE_STRING = "https://doctor-dyorina.tilda.ws/";
    public static final String DEFAULT_STRING = "Извини, к сожалению, я не знаю, что с этим делать \uD83E\uDEE0";
    public static final String NOT_FILE = "Это не то что я жду. Пришлите, пожалуйста, файл с расшрением pdf :)";

    //Default info product button.
    public static final String DEFAULT_PRODUCT_FILE_ID = "null";
    public static final String DEFAULT_PRODUCT_NAME = "Название товара";
    public static final String DEFAULT_DESCRIPTION = "Описание товара.";
    public static final BigDecimal DEFAULT_PRODUCT_PRICE = BigDecimal.valueOf(100);

    //Update, delete button
    public static final String UPDATE_MESSAGE = "Что хотите изменить?";
    public static final String DELETE_MESSAGE = "Товар удалён.";
    public static final String UPDATE_GENERAL_INLINE = "Изменить товар.";
    public static final String UPDATE_FILE_INLINE = "Добавить файл.";
    public static final String UPDATE_NAME_INLINE = "Изменить название.";
    public static final String UPDATE_DESCRIPTION_INLINE = "Изменить описание.";
    public static final String UPDATE_PRICE_INLINE = "Изменить стоймость.";
    public static final String DELETE_INLINE = "Удалить товар.";

    //Update messages
    public static final String UPDATE_NAME_FILE_ID_HANDLE = "Отправьте новый файл: ";
    public static final String UPDATE_NAME_HANDLE = "Введите новое имя: ";
    public static final String UPDATE_DESCR_HANDLE = "Введите новое описание: ";
    public static final String UPDATE_PRICE_HANDLE = "Введите новую стоймость: ";
    public static final String UPDATE_FILE_ID_MSG = "Файл добавлен!";
    public static final String UPDATE_NAME_MSG = "Название изменено!";
    public static final String UPDATE_DESCRIPTION_MSG = "Описание изменено!";
    public static final String UPDATE_PRICE_MSG = "Стоймость изменена!";

}
