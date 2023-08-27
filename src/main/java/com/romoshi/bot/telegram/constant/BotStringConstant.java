package com.romoshi.bot.telegram.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@NoArgsConstructor
public class BotStringConstant {

    String START_STRING = "123";
    String SALE_STRING = "Товар куплен!";
    String DEFAULT_STRING = "Извини, но я не знаю, что с этим делать \uD83E\uDEE0";
}
