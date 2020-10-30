package ru.armishev.IPet.views;

import ru.armishev.IPet.dao.log.LogPetActionDAO;
import ru.armishev.IPet.dao.log.LogRepository;
import ru.armishev.IPet.entity.pet.IPet;

import java.time.Instant;
import java.util.List;

public class LogView implements ILogView {
    private LogRepository logRepository;
    private IPet pet;

    public LogView(LogRepository logRepository, IPet pet) {
        this.logRepository = logRepository;
        this.pet = pet;
    }

    @Override
    public String getPetLifeHtml() {
        StringBuilder sb = new StringBuilder();
        List<LogPetActionDAO> petAction = getLastTimePetAction();

        sb.append("<div class='pet_action_list'>");
        if (petAction != null) {
            for (LogPetActionDAO log : petAction) {
                sb.append("<div class='pet_action_list_elem'>")
                        .append("<span>").append(log.getTimestamp()).append("</span>")
                        .append("<span>").append(" : ").append("</span>")
                        .append("<span>").append(log.getMessage()).append("</span>")
                        .append("</div>");
            }
        }
        sb.append("</div>");

        return sb.toString();
    }

    private List<LogPetActionDAO> getLastTimePetAction() {
        long period_before = 15;
        long start_timestamp = Instant.now().getEpochSecond() - period_before;

        return logRepository.findPetActionList(pet.getId(), start_timestamp);
    }
}
