package ru.armishev.IPet.entity.universe;

import org.springframework.stereotype.Service;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.pet.Pet;

import java.time.Instant;

@Service
public class Universe implements IUniverse {
    private static long timeIntervalEat = 5;

    @Override
    public void setEvent() {

    }

    @Override
    public void checkPet(IPet pet) {
        checkHungry(pet);
    }

    private void checkHungry(IPet pet) {
        Pet.Downtime petDowntime = pet.getDowntime();

        long current_time = Instant.now().getEpochSecond();
        long donwtime_range = current_time - petDowntime.getLasTimeStarvation();

        int count_of_iteration = (int)Math.floor(donwtime_range/timeIntervalEat);
        for(int i=0; i <= count_of_iteration; i++) {
            pet.starving();
        }

        petDowntime.increaseLasTimeStarvation(timeIntervalEat*count_of_iteration);
    }
}
