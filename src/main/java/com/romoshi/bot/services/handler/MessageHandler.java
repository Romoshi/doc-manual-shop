package com.romoshi.bot.services.handler;

import com.romoshi.bot.entity.Product;
import com.romoshi.bot.services.AdminService;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.PaymentService;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.command.message.Command;
import com.romoshi.bot.services.command.message.CommandFactory;
import com.romoshi.bot.services.command.update.UpdateFactory;
import com.romoshi.bot.services.command.update.UpdateProduct;
import com.romoshi.bot.services.file.FileSenderService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;

import java.math.BigDecimal;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageHandler {

    private final PaymentService paymentService;
    private final ProductService productService;
    private final FileSenderService fileSenderService;

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

        if(message.hasSuccessfulPayment()) {
            return pay(message);
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

    private BotApiMethod<?> pay(Message message) {
        SuccessfulPayment successfulPayment = message.getSuccessfulPayment();

        paymentService.savePaymentInfo(successfulPayment.getTelegramPaymentChargeId(),
                BigDecimal.valueOf(successfulPayment.getTotalAmount()),
                successfulPayment.getCurrency());

        String chatId = message.getChatId().toString();

        Product product = productService.getProductById(Long.parseLong(successfulPayment.getInvoicePayload()));

        try {
            fileSenderService.sendFileToChat(chatId, product.getFileId());
            return sendMsg(message, BotStringConstant.SALE_STRING);
        } catch (NullPointerException ex) {
            log.error("Файл не найден", ex);
        }

        return null;
    }
}
