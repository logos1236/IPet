package ru.armishev.IPet.entity.universe;

import ru.armishev.IPet.entity.pet.IPet;

public interface IUniverse {
    void timeMachine();
    boolean isGenerateDataByEvent(IPet pet);
}
