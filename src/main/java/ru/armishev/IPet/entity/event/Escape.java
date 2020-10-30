package ru.armishev.IPet.entity.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.dao.LogPetAction;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
import java.util.function.Consumer;

@Service
@Scope("prototype")
public class Escape extends Event {
    private long timeInterval = 1;
    private Consumer<IPet> cons = (pet) -> {
        pet.escape();

        afterSuccessAction();
    };

    @Override
    protected boolean isHappenedEvent() {
        boolean result = false;
        if (pet.getSatiety() < 90) {
            result = true;
        }

        return result;
    }

    private void afterSuccessAction() {
        long current_time = Instant.now().getEpochSecond();
        getLogRepository().save(new LogPetAction(pet.getId(), current_time, "Питомец сбежал"));
    }

    protected long getEventTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
