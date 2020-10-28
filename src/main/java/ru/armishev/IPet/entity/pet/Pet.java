package ru.armishev.IPet.entity.pet;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Pet implements IPet {
    private static final int HAPPINESS_MAX = 100;
    private static final int HEALTH_MAX = 100;
    private static final int SATIETY_MAX = 100;

    private long id;
    private String name;
    private long birth_date;
    private int health;
    private int satiety;
    private int happiness;
    private long last_time_of_event;

    public long getDowntime() {
        return System.currentTimeMillis() - this.last_time_of_event;
    }

    private void increaseHappiness(int happiness) {
        int tmp_happiness = this.happiness + happiness;
        tmp_happiness = (tmp_happiness > HAPPINESS_MAX) ? HAPPINESS_MAX : tmp_happiness;

        this.happiness = tmp_happiness;
    }

    private void decreaseHappiness(int happiness) {
        int tmp_happiness = this.happiness - happiness;
        tmp_happiness = (tmp_happiness < 0 ) ? 0 : tmp_happiness;

        this.happiness = tmp_happiness;
    }

    private void increaseHealth(int health) {
        int tmp_health = this.health + health;
        tmp_health = (tmp_health > HEALTH_MAX) ? HEALTH_MAX : tmp_health;

        this.health = tmp_health;
    }

    private void decreaseHealth(int health) {
        int tmp_health = this.health - health;
        tmp_health = (tmp_health < 0) ? 0 : tmp_health;

        this.health = tmp_health;
    }

    private void increaseSatiety(int satiety) {
        int tmp_satiety = this.satiety + satiety;
        tmp_satiety = (tmp_satiety > SATIETY_MAX) ? SATIETY_MAX : tmp_satiety;

        this.satiety = tmp_satiety;
    }

    private void decreaseSatiety(int satiety) {
        int tmp_satiety = this.satiety - satiety;
        tmp_satiety = (tmp_satiety < 0) ? 0 : tmp_satiety;

        this.satiety = tmp_satiety;
    }

    @Override
    public boolean isAlive() {
        return (health > 0) ? true : false;
    }

    @Override
    public long getAge() {
        return System.currentTimeMillis() - birth_date;
    }

    @Override
    public void birth() {
        this.birth_date = System.currentTimeMillis();
        this.health = 100;
        this.satiety = 100;
        this.happiness = HAPPINESS_MAX;
    }

    @Override
    public void eat(int satiety) {
        increaseSatiety(satiety);
        increaseHappiness(5);
    }

    @Override
    public void play() {
        decreaseSatiety(35);
        increaseHappiness(25);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth_date=" + birth_date +
                ", health=" + health +
                ", satiety=" + satiety +
                ", happiness=" + happiness +
                ", last_time_of_visit=" + last_time_of_event +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
