package ru.armishev.IPet.entity.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.dao.log.LogPetActionDAO;
import ru.armishev.IPet.entity.pet.IPet;

import java.util.Random;
import java.util.function.Consumer;

@Service
@Scope("prototype")
public class Starvation extends Event {
    private int probability = 100;
    private long timeInterval = 5;
    private Consumer<IPet> cons = (pet) -> {
        pet.starving();
        afterSuccessAction();
    };

    @Override
    protected boolean isHappenedEvent() {
        boolean result = false;

        Random r = new Random();
        int i = r.ints(0, (100 + 1)).findFirst().getAsInt();

        if (i <= probability) {
            result = true;
        }

        return result;
    }

    private void afterSuccessAction() {
        long current_time = System.currentTimeMillis();
        if (getLogRepository() != null) {
            getLogRepository().save(new LogPetActionDAO(pet.getId(), current_time, pet.getName() + " проголодался"));
        }
    }

    protected long getEventTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
