package ru.armishev.IPet.entity.pet.downTime;

public interface IDowntime {
    long getLasTimeStarvation();
    void increaseLasTimeStarvation(long timeStarvation);
}
