package com.romoshi.bot.session;

import org.springframework.stereotype.Component;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();
    private final Lock lock = new ReentrantLock();

    public UserContext getUserContext() {
        return userContextThreadLocal.get();
    }

    public void setUserContext(UserContext userContext) {
        lock.lock();

        try {
            userContextThreadLocal.set(userContext);
        } finally {
            lock.unlock();
        }
    }

    public void clearUserContext() {
        lock.lock();

        try {
            userContextThreadLocal.remove();
        } finally {
            lock.unlock();
        }
    }
}
