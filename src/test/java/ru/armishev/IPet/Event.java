package ru.armishev.IPet;

import org.junit.jupiter.api.Test;
import ru.armishev.IPet.entity.event.Boring;
import ru.armishev.IPet.entity.event.Escape;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.event.Starvation;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;

import static org.junit.Assert.assertTrue;

public class Event {
    /*
    Тест плохого обращения с животным
     */
    @Test
    public void badMasterTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        Escape event = new Escape();
        event.setPet(test);
        event.execute(Instant.now().getEpochSecond());

        for (int i = 0; i < 100; i ++) {
            test.starving(100);
        }

        event.execute(Instant.now().getEpochSecond()+10);
        assertTrue(test.isEscaped());
    }

    /*
    Тест события голода
     */
    @Test
    public void starvingTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        Starvation event = new Starvation();
        event.setPet(test);

        int start_satiety = test.getSatiety();
        for (int i = 0; i < 100; i ++) {
            event.execute(Instant.now().getEpochSecond()+10*i);
        }

        int end_satiety = test.getSatiety();
        assertTrue(start_satiety > end_satiety);
    }

    /*
    Тест события скуки
     */
    @Test
    public void boringTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        Boring event = new Boring();
        event.setPet(test);

        int start_happiness = test.getHappiness();

        for (int i = 0; i < 100; i ++) {
            event.execute(Instant.now().getEpochSecond()+10*i);
        }

        int end_happiness = test.getHappiness();

        assertTrue(start_happiness > end_happiness);
    }
}
