package ru.armishev.IPet.entity.event;

import ru.armishev.IPet.entity.pet.IPet;

import java.util.Random;
import java.util.function.Consumer;

public class PlayWithToy extends Event {
    private int probability = 30;
    private long timeInterval = 5;
    private Consumer<IPet> cons = (pet) -> {
        System.out.println("Is happened"+PlayWithToy.class);
        pet.play(20);
    };

    public PlayWithToy(IPet pet) {
        super(pet);
    }

    @Override
    protected boolean isHappenedEvent() {
        boolean result = false;

        Random r = new Random();
        int i = r.ints(0, (100 + 1)).findFirst().getAsInt();

        if (i <= probability) {
            result = true;
        }

        return result;
    };

    protected long getTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
