package ru.armishev.IPet.entity.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.dao.log.LogPetActionDAO;
import ru.armishev.IPet.entity.pet.IPet;

import java.util.Random;
import java.util.function.Consumer;

@Service
@Scope("prototype")
public class PlayAlone extends Event {
    private int probability = 50;
    private long timeInterval = 5;
    private Consumer<IPet> cons = (pet) -> {
        pet.play(20);

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
        getLogRepository().save(new LogPetActionDAO(pet.getId(), current_time, "Побегал по комнате"));
    }

    protected long getEventTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
