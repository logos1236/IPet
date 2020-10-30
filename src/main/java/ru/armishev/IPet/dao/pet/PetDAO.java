package ru.armishev.IPet.dao.pet;

import ru.armishev.IPet.entity.event.IEvent;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
Вспомогательная сущность для связи питомца в сессии и базе
*/

@Entity
@Table(name = "PET")
public class PetDAO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String name;
    private long birth_date;
    private int health;
    private int satiety;
    private int happiness;
    private long last_visit_time;
    private boolean is_escaped = false;

    @ElementCollection
    @CollectionTable(name = "event_time_list_mapping", joinColumns = {@JoinColumn(name = "pet_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "event_class")
    @Column(name = "event_time")
    private Map<Class<? extends IEvent>, Long> event_time_list = new HashMap<>();

    public Map<Class<? extends IEvent>, Long> getEvent_time_list() {
        return event_time_list;
    }

    public void setEvent_time_list(Map<Class<? extends IEvent>, Long> event_time_list) {
        this.event_time_list = event_time_list;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(long birth_date) {
        this.birth_date = birth_date;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public long getLast_visit_time() {
        return last_visit_time;
    }

    public void setLast_visit_time(long last_visit_time) {
        this.last_visit_time = last_visit_time;
    }

    public boolean isIs_escaped() {
        return is_escaped;
    }

    public void setIs_escaped(boolean is_escaped) {
        this.is_escaped = is_escaped;
    }

    @Override
    public String toString() {
        return "PetDAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth_date=" + birth_date +
                ", health=" + health +
                ", satiety=" + satiety +
                ", happiness=" + happiness +
                ", last_visit_time=" + last_visit_time +
                ", is_escaped=" + is_escaped +
                '}';
    }
}
