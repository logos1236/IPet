package ru.armishev.IPet.entity.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.armishev.IPet.dao.pet.PetDAO;
import ru.armishev.IPet.dao.pet.PetRepository;
import ru.armishev.IPet.entity.event.IEvent;

import java.time.Instant;
import java.util.*;

/*
Питомец
*/

@Service
@SessionScope
public class Pet implements IPet {
    @Autowired
    private PetRepository petRepository;

    private static final int HAPPINESS_MAX = 100;
    private static final int HEALTH_MAX = 100;
    private static final int SATIETY_MAX = 100;
    private static final int SATIETY_DECREASE = 5;
    private static final int SATIETY_HEALTH_DECREASE = 5;

    private long id;
    private String name;
    private long birth_date;
    private int health = HEALTH_MAX;
    private int satiety = SATIETY_MAX;
    private int happiness = HAPPINESS_MAX;
    private long last_visit_time;
    private boolean is_escaped = false;
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
    public boolean isEscaped() {
        return is_escaped;
    }

    @Override
    public void escape() {
        is_escaped = true;
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
    public void birth(String name) {
        this.name = name;
        this.birth_date = Instant.now().getEpochSecond();
        this.health = 100;
        this.satiety = 100;
        this.happiness = HAPPINESS_MAX;
        this.is_escaped = false;
        this.last_visit_time = Instant.now().getEpochSecond();
        this.event_time_list = new HashMap<>();

        PetDAO petDAO = saveToDAO();
        getPetFromDAO(petDAO);
    }

    /*
    Сохраняем данные питомца в базу
    */
    public PetDAO saveToDAO() {
        PetDAO petDAO = new PetDAO();

        if (this.id > 0) {
            petDAO.setId(this.id);
        }
        petDAO.setName(this.name);
        petDAO.setBirth_date(this.birth_date);
        petDAO.setHealth(this.health);
        petDAO.setSatiety(this.satiety);
        petDAO.setHappiness(this.happiness);
        petDAO.setLast_visit_time(this.last_visit_time);
        petDAO.setIs_escaped(this.is_escaped);
        petDAO.setEvent_time_list(this.event_time_list);

        petDAO = petRepository.save(petDAO);

        return petDAO;
    }

    /*
    Получаем данные питомца из базы
    */
    private void getPetFromDAO(PetDAO petDAO) {
        this.id = petDAO.getId();
        this.name = petDAO.getName();
        this.birth_date = petDAO.getBirth_date();
        this.health = petDAO.getHealth();
        this.satiety = petDAO.getSatiety();
        this.happiness = petDAO.getHappiness();
        this.last_visit_time = petDAO.getLast_visit_time();
        this.is_escaped = petDAO.isIs_escaped();
    }

    @Override
    public void loadFromDAO(long id) throws IllegalArgumentException {
        Optional<PetDAO> petDAO = petRepository.findById(id);
        if (petDAO.isPresent()) {
            getPetFromDAO(petDAO.get());
        } else {
            throw new IllegalArgumentException("Нет питомца с таким id");
        }
    }

    @Override
    public long getBirth_date() {
        return birth_date;
    }

    @Override
    public void loadFromPet(IPet another_pet) {
        this.id = another_pet.getId();
        this.name = another_pet.getName();
        this.birth_date = another_pet.getBirth_date();
        this.health = another_pet.getHeath();
        this.satiety = another_pet.getSatiety();
        this.happiness = another_pet.getHappiness();
        this.last_visit_time = another_pet.getLastVisitTime();
        this.is_escaped = another_pet.isEscaped();
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
    public void starving(int val) {
        decreaseSatiety(val);
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
