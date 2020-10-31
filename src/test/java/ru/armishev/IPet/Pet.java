package ru.armishev.IPet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.armishev.IPet.dao.pet.PetDAO;
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
	Продолжительный голод уменьшает уровень здоровья
	 */
    @Test
    public void decreaseHealthTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        int start_health = test.getHeath();

        test.starving(100);
        test.starving(100);
        test.starving(100);
        test.starving(100);

        int end_health = test.getHeath();

        assertTrue(start_health > end_health);
    }

    /*
	Кормление восстанавливает уровень сытости
	 */
    @Test
    public void eatTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        test.starving(50);
        int start_satiety = test.getSatiety();

        test.eat(50);

        int end_satiety = test.getHeath();

        assertTrue(start_satiety < end_satiety);
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

    /*
	Игра должна еличивать уровень счастья
	*/
    @Test
    public void playHappyTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();
        test.boring();

        int start_happy = test.getHappiness();

        test.play(100);

        int end_happy = test.getHappiness();


        assertTrue(start_happy < end_happy);
    }

    /*
    Тест конструктора. ID питомец должен получить только из базы
     */
    @Test
    public void constructorTest() {
        IPet test = new ru.armishev.IPet.entity.pet.Pet();

        assertEquals(test.getId(), 0);
    }
}
