package com.romoshi.bot.telegram.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@NoArgsConstructor
public class CommandConstant {
    String prefix = "/";

    String START_COMMAND = prefix + "start";
    String PRODUCTS_COMMAND = "Товары";
    String SITE_COMMAND = prefix + "site";
}
