package com.romoshi.bot.services.command.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowSiteCommandTest {

    private ShowSiteCommand showSiteCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        showSiteCommand = new ShowSiteCommand();
    }

    @Test
    void execute() {
        Chat chat = new Chat(123456789L, "Сайт");

        Message message = new Message();
        message.setChat(chat);

        BotApiMethod<?> result = showSiteCommand.execute(message);

        assertTrue(result instanceof SendMessage);
    }
}