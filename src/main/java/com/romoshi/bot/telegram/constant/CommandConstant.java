package com.romoshi.bot.telegram.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@NoArgsConstructor
public class CommandConstant {
    String START_COMMAND = "123";
    String DEFAULT_COMMAND = "Извини, но я не знаю, что с этим делать \uD83E\uDEE0";
}
