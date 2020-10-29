package ru.armishev.IPet.entity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
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
public class PlayAlone extends Event {
    @Autowired
    public LogRepository repository;

    private int probability = 30;
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

    @Lookup
    protected LogRepository getLogRepository() {return null;};

    private void afterSuccessAction() {
        long current_time = Instant.now().getEpochSecond();
        getLogRepository().save(new LogAction(pet.getId(), current_time, "Побегал по комнате"));
    }

    protected long getTimeInterval() {
        return timeInterval;
    }

    protected Consumer<IPet> getCons() {
        return cons;
    }
}
