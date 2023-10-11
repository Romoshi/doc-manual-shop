package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.PaymentService;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.services.command.update.UpdateFactory;
import com.romoshi.bot.services.command.update.UpdateProduct;
import com.romoshi.bot.services.utils.AdminUtil;
import com.romoshi.bot.session.UserContext;
import com.romoshi.bot.session.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Service
@Slf4j
public class MessageHandler {

    private final PaymentService paymentService;
    private final AdminUtil adminUtil;

    private final CommandFactory commandFactory;
    private final UpdateFactory updateFactory;

    public static UserContextHolder userContextHolder = new UserContextHolder();

    @Autowired
    public MessageHandler(PaymentService paymentService, CommandFactory commandFactory,
                          UpdateFactory updateFactory, AdminUtil adminUtil) {
        this.paymentService = paymentService;
        this.commandFactory = commandFactory;
        this.updateFactory = updateFactory;
        this.adminUtil = adminUtil;
    }

    public BotApiMethod<?> processMessage(Message message) {
        UserContext userContext = getContext(message);

        String state = userContext.getCurrentState();
        String action = userContext.getAction();

        if(message.hasSuccessfulPayment()) {
            return paymentService.pay(message);
        }
        else if(Objects.equals(state, "admin") && action != null) {
            String[] data = action.split("_");

            UpdateProduct updateProduct = updateFactory.createUpdate(data[0]);
            return updateProduct.update(message);
        }
        else {
            Command command = commandFactory.createCommand(message.getText());
            return command.execute(message);
        }
    }

    private UserContext getContext(Message message) {
        String chatId = message.getChatId().toString();

        UserContext userContext = userContextHolder.getUserContext(chatId);

        if (userContext == null && adminUtil.isAdmin(chatId)) {
            userContext = new UserContext();
            userContext.setCurrentState("admin");
            userContextHolder.setUserContext(chatId, userContext);
        } else if (userContext == null) {
            userContext = new UserContext();
            userContext.setCurrentState("user");
            userContextHolder.setUserContext(chatId, userContext);
        }

        return userContext;
    }
}
