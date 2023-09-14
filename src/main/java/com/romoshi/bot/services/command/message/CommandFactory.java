package com.romoshi.bot.services.command.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandFactory {

    private final Map<String, Command> commandMap;

    @Autowired
    public CommandFactory(List<Command> commandList) {
        this.commandMap = new HashMap<>();
        for (Command command : commandList) {
            this.commandMap.put(command.getCommandName(), command);
        }
    }

    public Command createCommand(String commandText) {
        return commandMap.getOrDefault(commandText, new DefaultCommand());
    }
}
