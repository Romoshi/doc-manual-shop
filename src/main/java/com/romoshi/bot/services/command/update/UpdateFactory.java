package com.romoshi.bot.services.command.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateFactory {

    private final Map<String, UpdateProduct> updateProductMap;

    @Autowired
    public UpdateFactory(List<UpdateProduct> updateProductList) {
        this.updateProductMap = new HashMap<>();
        for (UpdateProduct update : updateProductList) {
            this.updateProductMap.put(update.getUpdateName(), update);
        }
    }

    public UpdateProduct createUpdate(String data) {
        return updateProductMap.get(data);
    }
}
