package com.romoshi.bot.services.command.message;

import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.utils.AdminUtil;
import com.romoshi.bot.telegram.constant.CommandConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AddProductCommand implements Command {
    private final AdminService adminService;
    private final AdminUtil adminUtil;

    @Autowired
    public AddProductCommand(AdminService adminService, AdminUtil adminUtil) {
        this.adminService = adminService;
        this.adminUtil = adminUtil;
    }
    @Override
    public BotApiMethod<?> execute(Message message) {
        String chatId = message.getChatId().toString();

        if(adminUtil.isAdmin(chatId)) {
            return adminService.addProduct(message);
        }

        return null;
    }

    @Override
    public String getCommandName() {
        return CommandConstant.ADD_COMMAND;
    }
}
