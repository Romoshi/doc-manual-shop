package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.services.command.update.UpdateFactory;
import com.romoshi.bot.services.command.update.UpdateProduct;
import com.romoshi.bot.telegram.constant.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class MessageHandler {

    private final CommandFactory commandFactory;
    private final UpdateFactory updateFactory;

    private final AdminService adminService;
    private final AdminUtil adminUtil;

    public static String pendingAction = null;
    public static long pendingUserId = 0;

    public BotApiMethod<?> answerMessage(Message message) {
        String messageText = message.getText();
        String chatId = message.getChatId().toString();

        if(adminUtil.isAdmin(chatId)) {
            if(messageText.equals(CommandConstant.ADD_COMMAND)) {
                return adminService.addProduct(message);
            }
        }

        Command command = commandFactory.createCommand(messageText);
        return command.execute(message);
    }

    public BotApiMethod<?> pendingAction(Message message) {

        if (pendingAction != null && pendingUserId == message.getChatId()) {
            String[] data = pendingAction.split("_");
            UpdateProduct updateProduct = updateFactory.createUpdate(data[0]);

            return updateProduct.update(message);
        }

        return answerMessage(message);
    }

    public static void pendingSetNull() {
        pendingAction = null;
        pendingUserId = 0;
    }
}
