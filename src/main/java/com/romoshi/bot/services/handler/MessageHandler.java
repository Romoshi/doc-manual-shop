package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.PaymentService;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.services.command.update.UpdateFactory;
import com.romoshi.bot.services.command.update.UpdateProduct;
import com.romoshi.bot.telegram.constant.CommandConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
public class MessageHandler {

    private final PaymentService paymentService;

    private final CommandFactory commandFactory;
    private final UpdateFactory updateFactory;

    @Autowired
    public MessageHandler(PaymentService paymentService, CommandFactory commandFactory,
                          UpdateFactory updateFactory) {
        this.paymentService = paymentService;
        this.commandFactory = commandFactory;
        this.updateFactory = updateFactory;
    }

    public static String pendingAction = null;
    public static long pendingUserId = 0;

    public BotApiMethod<?> answerMessage(Message message) {
        String messageText = message.getText();

        Command command = commandFactory.createCommand(messageText);
        return command.execute(message);
    }

    public BotApiMethod<?> pendingAction(Message message) {

        if(message.hasSuccessfulPayment()) {
            return paymentService.pay(message);
        }

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
