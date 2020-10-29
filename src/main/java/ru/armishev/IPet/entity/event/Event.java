package ru.armishev.IPet.entity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.dao.LogRepository;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Scope("prototype")
public abstract class Event implements IEvent {
    protected IPet pet;

    protected abstract Consumer<IPet> getCons();

    protected abstract long getTimeInterval();

    public Event() {
        this.pet = pet;
    }

    public void setPet(IPet pet) {
        this.pet = pet;
    }

    /*
        Время последнего случившегося события
         */
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

    /*
    Обновляем последнего случившегося события
     */
    protected void increaseLastTimeExec(long time_interval) {
        Map<Class<? extends IEvent>, Long> event_time_list = pet.getEvent_time_list();
        long lastTimeExec = event_time_list.get(this.getClass());

        lastTimeExec += time_interval;

        pet.setEvent_time_list(this.getClass(), lastTimeExec);
    }

    /*
    Вероятность события произойти
    */
    protected abstract boolean isHappenedEvent();

    @Override
    public void execute(long current_time) {
        long downtime_range = current_time - getLastTimeExec();

        if((int)Math.floor(downtime_range/getTimeInterval()) > 0) {
            if (isHappenedEvent()) {
                getCons().accept(pet);
            }
            increaseLastTimeExec(getTimeInterval());
        }
    }
}
