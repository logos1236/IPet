package ru.armishev.IPet.entity.universe;

import org.springframework.stereotype.Service;
import ru.armishev.IPet.entity.pet.downTime.Downtime;
import ru.armishev.IPet.entity.pet.downTime.IDowntime;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class Universe implements IUniverse {
    private static long timeIntervalEat = 5;
    private static long timeIntervalHappiness = 10;

    @Override
    public void setEvent() {

    }

    @Override
    public void checkPet(IPet pet) {
    }

    private void checkEvent(IPet pet, Predicate<IPet> pet_method, Function<IDowntime, Long> downtime_time) {

    };
}
