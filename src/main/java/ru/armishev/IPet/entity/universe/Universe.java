package ru.armishev.IPet.entity.universe;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.armishev.IPet.entity.event.EventFactory;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.pet.IPet;

/*
    Вселенная, которая создает для питомца случайные события
*/
@Service
@SessionScope
public class Universe implements IUniverse {
    @Autowired
    private IPet pet;

    @Autowired
    private EventFactory eventFactory;

    /*
    Эмулируем временной отрезок жизни питомца
    */
    @Override
    public void timeMachine() {
        long current_time = Instant.now().getEpochSecond();
        long last_visit_pet_time = pet.getLastVisitTime();

        if (last_visit_pet_time > 0) {
            for (long i_time = last_visit_pet_time; i_time <= current_time; i_time++) {
                if (!pet.isEscaped()) {
                    createEventInPetLive(i_time);
                }
            }
        }
    }

    /*
    Создаем события в жизни питомца
     */
    private void createEventInPetLive(long current_time) {
        List<IEvent> eventList = eventFactory.getEventList();

        if (!eventList.isEmpty()) {
            for(IEvent event: eventList) {
                if (!pet.isEscaped()) {
                    event.execute(current_time);
                }
            }
        }
    }
}
