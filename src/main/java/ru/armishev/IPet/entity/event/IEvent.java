package ru.armishev.IPet.entity.event;

import ru.armishev.IPet.entity.pet.IPet;

import java.util.function.Predicate;

public interface IEvent {
    void execute(long current_time);
}
