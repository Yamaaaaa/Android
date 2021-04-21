package com.example.lalala.entity;

import java.util.HashMap;
import java.util.Map;

public class UserActionData {
    int userId;
    Map<Integer, Boolean> actions = new HashMap<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, Boolean> getActions() {
        return actions;
    }

    public void setActions(Map<Integer, Boolean> actions) {
        this.actions = actions;
    }
}
