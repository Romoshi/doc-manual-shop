package com.romoshi.bot.services.command.message;

import com.romoshi.bot.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductsCommandTest {
    private ProductsCommand productsCommand;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productsCommand = new ProductsCommand(productService);
    }

    @Test
    void execute() {
        Chat chat = new Chat(123456789L, "/start");

        Message message = new Message();
        message.setChat(chat);

        BotApiMethod<?> result = productsCommand.execute(message);

        assertTrue(result instanceof SendMessage);
    }

}