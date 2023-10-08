package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.PaymentService;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.services.command.update.UpdateFactory;
import com.romoshi.bot.services.command.update.UpdateProduct;
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

    private final CommandFactory commandFactory;
    private final UpdateFactory updateFactory;

    public static UserContextHolder userContextHolder = new UserContextHolder();

    @Autowired
    public MessageHandler(PaymentService paymentService, CommandFactory commandFactory,
                          UpdateFactory updateFactory) {
        this.paymentService = paymentService;
        this.commandFactory = commandFactory;
        this.updateFactory = updateFactory;
    }

    public static String pendingAction = null;

    public BotApiMethod<?> processMessage(Message message) {


        UserContext userContext = userContextHolder.getUserContext();

        if (userContext == null) {
            userContext = new UserContext();
            userContext.setUserId(message.getChatId().toString());
            userContext.setCurrentState("user");
            userContextHolder.setUserContext(userContext);
        }

        String state = userContextHolder.getUserContext().getCurrentState();

        log.info(state);

        if(message.hasSuccessfulPayment()) {
            return paymentService.pay(message);
        }
        else if(!Objects.equals(state, "user")) {
            String[] data = state.split("_");

            UpdateProduct updateProduct = updateFactory.createUpdate(data[0]);
            return updateProduct.update(message);
        }
//        else if (pendingAction != null) {
//            String[] data = pendingAction.split("_");
//
//            UpdateProduct updateProduct = updateFactory.createUpdate(data[0]);
//            return updateProduct.update(message);
//        }
        else {
            Command command = commandFactory.createCommand(message.getText());
            return command.execute(message);
        }
    }

    public static void pendingSetNull() {
        pendingAction = null;
    }
}
