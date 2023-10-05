package com.romoshi.bot.services.command.message;

import com.romoshi.bot.services.utils.AdminUtil;
import com.romoshi.bot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandFactoryTest {

    private CommandFactory commandFactory;

    @Mock
    private AdminUtil adminUtil;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Command> commandList = List.of(
                new StartCommand(adminUtil, userService)
        );

        commandFactory = new CommandFactory(commandList);
    }

    @Test
    public void testCreateCommand_ExistingCommand() {
        String commandText = "/start";
        Command result = commandFactory.createCommand(commandText);

        assertEquals(result, commandFactory.createCommand(commandText));
    }

    @Test
    public void testCreateCommand_DefaultCommand() {
        String commandText = "/nonexistent";
        Command result = commandFactory.createCommand(commandText);

        assertEquals(result.getClass(), DefaultCommand.class);
    }
}