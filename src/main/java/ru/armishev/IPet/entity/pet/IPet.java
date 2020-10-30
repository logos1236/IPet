package ru.armishev.IPet.entity.pet;

import ru.armishev.IPet.entity.event.IEvent;

import java.util.Map;

public interface IPet {
    void birth();
    boolean isEscaped();
    void escape();

    String getName();
    void setName(String name);
    long getAge();
    int getHeath();
    int getSatiety();
    int getHappiness();
    long getId();

    void eat(int satiety);
    void starving();

    void play(int val);
    void play();
    void boring(int val);
    void boring();

    Map<Class<? extends IEvent>, Long> getEvent_time_list();
    void setEvent_time_list(Class<? extends IEvent> class_name, long time);

    long getLastVisitTime();
    void setLastVisitTime(long current_time);
}
