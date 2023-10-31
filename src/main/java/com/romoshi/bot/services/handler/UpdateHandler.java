package com.romoshi.bot.services.handler;

import com.romoshi.bot.services.PaymentService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
public class UpdateHandler {
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;
    private final PreCheckoutHandler preCheckoutHandler;
    private final PaymentService paymentService;

    public UpdateHandler(MessageHandler messageHandler, CallbackHandler callbackHandler,
                         PreCheckoutHandler preCheckoutHandler, PaymentService paymentService) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
        this.preCheckoutHandler = preCheckoutHandler;
        this.paymentService = paymentService;
    }

    public BotApiMethod<?> updateProcess(Update update) {
        if (update.hasMessage() && update.getMessage().hasSuccessfulPayment()) {
            return paymentService.pay(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            return callbackHandler.processCallbackQuery(update.getCallbackQuery());
        } else if (update.hasPreCheckoutQuery()) {
            return preCheckoutHandler.processPreCheckOut(update.getPreCheckoutQuery());
        } else if (update.hasMessage()) {
            return messageHandler.processMessage(update.getMessage());
        }

        return sendMsg(update.getMessage(), BotStringConstant.DEFAULT_STRING);
    }
}
