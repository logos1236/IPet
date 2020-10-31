package ru.armishev.IPet;

import org.junit.jupiter.api.Test;
import ru.armishev.IPet.entity.event.Escape;
import ru.armishev.IPet.entity.event.IEvent;
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
}
