package ru.armishev.IPet.entity.event;

import ru.armishev.IPet.entity.pet.IPet;

import java.util.function.Predicate;

public interface IEvent {
    void setPet(IPet pet);
    void execute(long current_time);
}
