package ru.armishev.IPet.entity.pet.downTime;

/*
Время простоя животного
 */
public class Downtime {
    private long lasTimeStarvation;
    private long lasTimeHappiness;

    public long getLasTimeStarvation() {
        return lasTimeStarvation;
    }

    public void setLasTimeStarvation(long lasTimeStarvation) {
        this.lasTimeStarvation = lasTimeStarvation;
    }

    public void increaseLasTimeStarvation(long timeStarvation) {
        this.lasTimeStarvation += timeStarvation;
    }

    Downtime(long current_time) {
        this.lasTimeStarvation = current_time;
    }

    @Override
    public String toString() {
        return "Downtime{" +
                "timeEat=" + lasTimeStarvation +
                '}';
    }
}
