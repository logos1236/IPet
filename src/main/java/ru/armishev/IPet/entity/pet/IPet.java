package ru.armishev.IPet.entity.pet;

public interface IPet {
    boolean isAlive();
    long getAge();
    void birth();
    void eat(int satiety);
    void play();
    long getDowntime();
}
