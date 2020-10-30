package ru.armishev.IPet.entity.universe;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.entity.event.Boring;
import ru.armishev.IPet.entity.event.EventFactory;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.event.Starvation;
import ru.armishev.IPet.entity.pet.IPet;

@Service
public class Universe implements IUniverse {
    private IPet pet;

    @Autowired
    EventFactory eventFactory;

    @Override
    public void timeMachine(IPet pet) {
        this.pet = pet;

        long current_time = Instant.now().getEpochSecond();
        long last_visit_pet_time = pet.getLastVisitTime();

        for(long i = last_visit_pet_time; i <= current_time; i++) {
            checkPet(i);
        }
    }

    private void checkPet(long current_time) {
        List<IEvent> eventList = eventFactory.getEventList();

        if (!eventList.isEmpty()) {
            for(IEvent event: eventList) {
                event.setPet(pet);
                event.execute(Instant.now().getEpochSecond());
            }
        }
    }
}
