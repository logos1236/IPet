package ru.armishev.IPet.entity.pet;

import ru.armishev.IPet.entity.event.IEvent;

import java.util.Map;

public interface IPet {
    boolean isAlive();
    long getAge();
    void birth();

    void eat(int satiety);
    void starving();

    void play(int val);
    void boring(int val);

    Map<Class<? extends IEvent>, Long> getEvent_time_list();
    void setEvent_time_list(Class<? extends IEvent> class_name, long time);

    long getLastVisitTime();
    void setLastVisitTime(long current_time);
}
