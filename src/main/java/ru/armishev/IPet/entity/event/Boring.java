package ru.armishev.IPet.entity.event;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.dao.LogPetActionDAO;
import ru.armishev.IPet.dao.LogRepository;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
import java.util.Random;
import java.util.function.Consumer;

@Service
@Scope("prototype")
public class Boring extends Event  {
    private int probability = 50;
    private long timeInterval = 10;
    private Consumer<IPet> cons = (pet) -> {
        pet.boring();

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
        //long current_time = Instant.now().getEpochSecond();
        long current_time = System.currentTimeMillis();
        getLogRepository().save(new LogPetActionDAO(pet.getId(), current_time, "Скучает"));
    }

    protected long getEventTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
