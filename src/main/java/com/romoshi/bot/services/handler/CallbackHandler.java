package com.romoshi.bot.services.handler;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import com.romoshi.bot.services.command.callback.Action;
import com.romoshi.bot.services.command.callback.ActionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackHandler {

    final ProductService productService;
    private final ActionFactory actionFactory;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("_");
        List<Product> products = productService.getAllProducts();

        for(Product product : products) {
            Action action = actionFactory.createAction(data[0]);
            return action.execute(callbackQuery, product);
        }

        return null;
    }
}
