package ru.armishev.IPet.entity.pet;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.armishev.IPet.entity.event.IEvent;

import java.time.Instant;
import java.util.*;

@Service
@SessionScope
public class Pet implements IPet {
    private static final int HAPPINESS_MAX = 100;
    private static final int HEALTH_MAX = 100;
    private static final int SATIETY_MAX = 100;
    private static final int SATIETY_DECREASE = 5;
    private static final int SATIETY_HEALTH_DECREASE = 5;

    private long id;
    private String name;
    private long birth_date;
    private int health;
    private int satiety;
    private int happiness;
    private long last_visit_time;

    private Map<Class<? extends IEvent>, Long> event_time_list = new HashMap<>();

    public Map<Class<? extends IEvent>, Long> getEvent_time_list() {
        return event_time_list;
    }

    public void setEvent_time_list(Class<? extends IEvent> class_name, long time) {
        event_time_list.put(class_name, time);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getLastVisitTime() {
        return last_visit_time;
    }

    @Override
    public void setLastVisitTime(long current_time) {
        last_visit_time = current_time;
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

        if (tmp_satiety == 0) {
            decreaseHealth(SATIETY_HEALTH_DECREASE);
        }

        this.satiety = tmp_satiety;
    }

    @Override
    public boolean isAlive() {
        return (health > 0) ? true : false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getAge() {
        return Instant.now().getEpochSecond() - birth_date;
    }

    public long getId() {
        return id;
    }

    @Override
    public void birth() {
        this.id = 1;
        this.birth_date = Instant.now().getEpochSecond();
        this.health = 100;
        this.satiety = 100;
        this.happiness = HAPPINESS_MAX;
        this.last_visit_time = Instant.now().getEpochSecond();
    }

    @Override
    public int getHeath() {
        return this.health;
    }

    @Override
    public int getSatiety() {
        return this.satiety;
    }

    @Override
    public int getHappiness() {
        return this.happiness;
    }

    @Override
    public void eat(int satiety) {
        increaseSatiety(satiety);
        increaseHappiness(5);
    }

    @Override
    public void starving() {
        decreaseSatiety(SATIETY_DECREASE);
    }

    @Override
    public void play(int val) {
        decreaseSatiety((int)(val*1.5));
        increaseHappiness(val);
    }

    @Override
    public void play() {
        Random r = new Random();
        int rand_i = r.ints(0, (20 + 1)).findFirst().getAsInt();

        play(rand_i);
    }

    @Override
    public void boring(int val) {
        decreaseHappiness(val);
    }

    @Override
    public void boring() {
        Random r = new Random();
        int rand_i = r.ints(0, (20 + 1)).findFirst().getAsInt();

        boring(rand_i);
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
