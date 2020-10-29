package ru.armishev.IPet.entity.pet;

import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.pet.downTime.Downtime;

import java.util.Map;

public interface IPet {
    boolean isAlive();
    long getAge();
    void birth();

    void eat(int satiety);
    void starving();

    void play();
    void boring();

    Map<Class<? extends IEvent>, Long> getEvent_time_list();
    void setEvent_time_list(Class<? extends IEvent> class_name, long time);
}
