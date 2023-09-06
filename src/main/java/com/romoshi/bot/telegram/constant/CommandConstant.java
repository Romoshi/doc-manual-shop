package com.romoshi.bot.telegram.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
public class CommandConstant {
    public static final String prefix = "/";
    public static final String START_COMMAND = prefix + "start";
    public static final String PRODUCTS_COMMAND = "Товары";
    public static final String SHOW_SITE_COMMAND = "Сайт";
    public static final String ADD_COMMAND = "Добавить товар";
}
