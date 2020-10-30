package ru.armishev.IPet.entity.universe;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.armishev.IPet.entity.event.Boring;
import ru.armishev.IPet.entity.event.EventFactory;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.event.Starvation;
import ru.armishev.IPet.entity.pet.IPet;

@Service
@SessionScope
public class Universe implements IUniverse {
    @Autowired
    private IPet pet;

    @Autowired
    private EventFactory eventFactory;

    @Override
    public void timeMachine() {
        long current_time = Instant.now().getEpochSecond();
        long last_visit_pet_time = pet.getLastVisitTime();

        if (last_visit_pet_time > 0) {
            for (long i_time = last_visit_pet_time; i_time <= current_time; i_time++) {
                checkPet(i_time);
            }
        }
    }

    private void checkPet(long current_time) {
        List<IEvent> eventList = eventFactory.getEventList();

        System.out.println(pet.getName());

        if (!eventList.isEmpty()) {
            for(IEvent event: eventList) {
                event.execute(current_time);
            }
        }
    }
}
