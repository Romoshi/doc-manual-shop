package com.romoshi.bot.services.handler;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.UserService;
import com.romoshi.bot.services.command.callback.Action;
import com.romoshi.bot.services.command.callback.ActionFactory;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.ButtonConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Objects;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    final ProductService productService;
    final UserService userService;

    private final CommandFactory commandFactory;
    private final ActionFactory actionFactory;

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

            List<Product> products = productService.getAllProducts();
            for(Product product : products) {
                Action action = actionFactory.createAction(pendingAction);
                return action.update(message, product);
                }
            }

        return answerMessage(message);
    }

    public static void pendingSetNull() {
        pendingAction = null;
        pendingUserId = 0;
    }
}
