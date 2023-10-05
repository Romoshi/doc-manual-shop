package com.romoshi.bot.services.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminUtil {

    @Value("${developId}")
    private String adminID;

    public boolean isAdmin(String chatId) {
        return chatId.equals(adminID);
    }
}
