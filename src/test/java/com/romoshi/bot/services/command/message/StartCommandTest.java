package com.romoshi.bot.services.command.message;

import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class StartCommandTest {
    private StartCommand startCommand;

    @Mock
    private AdminUtil adminUtil;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        startCommand = new StartCommand(adminUtil, userService);
    }

    @Test
    void execute() {
        when(adminUtil.isAdmin(anyString())).thenReturn(true);

        Chat chat = new Chat(123456789L, "/start");

        Message message = new Message();
        message.setChat(chat);
        
        BotApiMethod<?> result = startCommand.execute(message);
        
        assertTrue(result instanceof SendMessage);
    }
}