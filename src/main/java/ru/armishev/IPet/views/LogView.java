package ru.armishev.IPet.views;

import ru.armishev.IPet.dao.LogPetAction;
import ru.armishev.IPet.dao.LogRepository;
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
        List<LogPetAction> petAction = getLastTimePetAction();

        sb.append("<div class='pet_action_list'>");
        if (petAction != null) {
            for (LogPetAction log : petAction) {
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

    private List<LogPetAction> getLastTimePetAction() {
        long period_before = 60*60*24;
        long start_timestamp = Instant.now().getEpochSecond() - period_before;

        return logRepository.findPetActionList(pet.getId(), start_timestamp);
    }
}
