package com.romoshi.bot.services.command.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActionFactory {

    private final Map<String, Action> actionMap;

    @Autowired
    public ActionFactory(List<Action> actionList) {
        this.actionMap = new HashMap<>();
        for (Action action : actionList) {
            this.actionMap.put(action.getActionName(), action);
        }
    }

    public Action createAction(String data) {
        return actionMap.getOrDefault(data, null);
    }

    public void clearAction() {
        actionMap.clear();
    }
}
