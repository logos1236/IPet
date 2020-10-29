package ru.armishev.IPet.entity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.dao.LogAction;
import ru.armishev.IPet.dao.LogRepository;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
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

    @Lookup
    protected LogRepository getLogRepository() {return null;};

    private void afterSuccessAction() {
        long current_time = Instant.now().getEpochSecond();
        getLogRepository().save(new LogAction(pet.getId(), current_time, "Проголодался"));
    }

    protected long getTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
