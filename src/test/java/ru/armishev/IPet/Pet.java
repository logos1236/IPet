package ru.armishev.IPet;

import org.junit.jupiter.api.Test;
import ru.armishev.IPet.entity.pet.IPet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Pet {
    /*
	Увеличивать уровень счастья можно только до определенного максимума
	 */
    @Test
    public void increaseHappinessTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        test.play(100);

        assertEquals(test.getHappiness(), 100);
    }

    /*
	Уменьшать уровень счастья можно только до определенного минимума
	 */
    @Test
    public void decreaseHappinessTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        test.boring(100);

        assertEquals(test.getHappiness(), 0);
    }

    /*
	Побег
	 */
    @Test
    public void isEscapedTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        test.escape();

        assertTrue(test.isEscaped());
    }

    /*
	Игра должна уменьшать уровень сытости
	*/
    @Test
    public void playTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        test.play(100);

        assertEquals(test.getSatiety(), 0);
    }
}
