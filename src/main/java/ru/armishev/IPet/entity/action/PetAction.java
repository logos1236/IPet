package ru.armishev.IPet.entity.action;

import java.util.HashMap;
import java.util.Map;

/*
Список действий, которые может генерировать пользователь для питомца
 */

public enum PetAction {
    FEED, PLAY;
    private static Map<PetAction, String> list = new HashMap<>();

    static {
        list.put(FEED, "Кормить");
        list.put(PLAY, "Играть");
    }

    public static Map<PetAction, String> getMap() {
        return list;
    }
}
