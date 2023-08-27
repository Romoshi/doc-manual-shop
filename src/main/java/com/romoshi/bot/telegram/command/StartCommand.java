package com.romoshi.bot.telegram.command;

public class StartCommand extends Handler {
    public void handleRequest(int request) {
        if (request >= 0 && request < 10) {

        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
