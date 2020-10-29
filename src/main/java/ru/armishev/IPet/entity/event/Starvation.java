package ru.armishev.IPet.entity.event;

import ru.armishev.IPet.entity.pet.IPet;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Starvation extends Event {
    protected long timeInterval = 10;
    protected Consumer<IPet> cons = (pet) -> {
        pet.starving();
    };

    public Starvation(IPet pet) {
        super(pet);
    }

    protected long getTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
