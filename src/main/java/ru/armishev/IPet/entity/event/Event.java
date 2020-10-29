package ru.armishev.IPet.entity.event;

import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Event implements IEvent {
    protected IPet pet;

    protected abstract Consumer<IPet> getCons();

    protected abstract long getTimeInterval();

    public Event(IPet pet) {
        this.pet = pet;
    }

    protected long getLastTimeExec() {
        Map<Class<? extends IEvent>, Long> event_time_list = pet.getEvent_time_list();

        long lastTimeExec = 0;
        if (event_time_list.containsKey(this.getClass())) {
            lastTimeExec = event_time_list.get(this.getClass());
        } else {
            lastTimeExec = Instant.now().getEpochSecond();
            pet.setEvent_time_list(this.getClass(), lastTimeExec);
        }

        return lastTimeExec;
    }

    protected void increaseLastTimeExec(long time_interval) {
        Map<Class<? extends IEvent>, Long> event_time_list = pet.getEvent_time_list();
        long lastTimeExec = event_time_list.get(this.getClass());

        lastTimeExec += time_interval;

        pet.setEvent_time_list(this.getClass(), lastTimeExec);
    }

    @Override
    public void execute(long current_time) {
        long downtime_range = current_time - getLastTimeExec();

        if((int)Math.floor(downtime_range/getTimeInterval()) > 0) {
            getCons().accept(pet);
            increaseLastTimeExec(getTimeInterval());
        }
    }
}
