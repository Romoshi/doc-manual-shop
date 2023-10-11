package com.romoshi.bot.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {

    private String currentState;
    private String action;
}
