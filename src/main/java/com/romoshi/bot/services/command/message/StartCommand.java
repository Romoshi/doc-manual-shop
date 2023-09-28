package com.romoshi.bot.services.command.message;

import com.romoshi.bot.entity.User;
import com.romoshi.bot.services.AdminUtil;
import com.romoshi.bot.services.UserService;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import com.romoshi.bot.telegram.constant.CommandConstant;
import com.romoshi.bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final AdminUtil adminUtil;
    final UserService userService;

    ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();
    @Override
    public BotApiMethod<?> execute(Message message) {
        String chatId = message.getChatId().toString();

        if(!userService.checkIfUserExists(chatId)) {
            User user = new User();
            user.setChatId(chatId);
            userService.saveUser(user);
        }

        SendMessage sendMessage = sendMsg(message, BotStringConstant.START_STRING);
        sendMessage.setReplyMarkup(replyKeyboardMaker.
                getReplyKeyboard(adminUtil.isAdmin(chatId)));

        return sendMessage;
    }

    public String getCommandName() {
        return CommandConstant.START_COMMAND;
    }
}
