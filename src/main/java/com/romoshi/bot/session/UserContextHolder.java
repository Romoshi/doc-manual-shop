package com.romoshi.bot.session;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class UserContextHolder {

    private static final ConcurrentHashMap<String, UserContext> userContextHashMap = new ConcurrentHashMap<>();

    public UserContext getUserContext(String chatId) {
        return userContextHashMap.get(chatId);
    }

    public void setUserContext(String chatId, UserContext userContext) {
        userContextHashMap.put(chatId, userContext);
    }

    public void updateUserContext(String chatId, String action) {
        userContextHashMap.compute(chatId, (key, userContext) -> {
            if (userContext != null) {
                userContext.setAction(action);
            }
            return userContext;
        });
    }

    public void clearActionUserContext(String chatId) {
        userContextHashMap.compute(chatId, (key, userContext) -> {
            if (userContext != null) {
                userContext.setAction(null);
            }
            return userContext;
        });
    }
}
