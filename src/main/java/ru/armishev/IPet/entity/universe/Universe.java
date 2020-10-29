package ru.armishev.IPet.entity.universe;

import java.time.Instant;
import org.springframework.stereotype.Service;
import ru.armishev.IPet.entity.event.Boring;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.event.Starvation;
import ru.armishev.IPet.entity.pet.IPet;

@Service
public class Universe implements IUniverse {
    @Override
    public void timeMachine(IPet pet) {
        long current_time = Instant.now().getEpochSecond();
        long last_visit_pet_time = pet.getLastVisitTime();

        for(long i = last_visit_pet_time; i <= current_time; i++) {
            checkPet(pet, i);
        }
    }

    private void checkPet(IPet pet, long current_time) {
        IEvent starvation = new Starvation(pet);
        starvation.execute(Instant.now().getEpochSecond());

        IEvent boring = new Boring(pet);
        boring.execute(Instant.now().getEpochSecond());
    }


}
