package ru.armishev.IPet.views;

import ru.armishev.IPet.entity.action.PetAction;
import ru.armishev.IPet.entity.pet.IPet;

import java.util.Map;

public class PetView implements IPetView {
    IPet pet;

    public PetView(IPet pet) {
        this.pet = pet;
    }

    @Override
    public String getHtml() {
        StringBuilder result = new StringBuilder();

        result.append(getStatusBar());

        return result.toString();
    }

    private StringBuilder getStatusBar() {
        StringBuilder result = new StringBuilder();

        result.append("<div class='status_bar_pet'><ul>")
                .append("<li>").append("Имя").append(" : ").append(pet.getName()).append("</li>")
                .append("<li>").append("Здоровье").append(" : ").append(pet.getHeath()).append("</li>")
                .append("<li>").append("Сытость").append(" : ").append(pet.getSatiety()).append("</li>")
                .append("<li>").append("Радость").append(" : ").append(pet.getHappiness()).append("</li>")
                .append("</ul></div>");

        return result;
    }

    @Override
    public String getHtmlControlPanel() {
        StringBuilder result = new StringBuilder();

        result.append("<form class='service-action-pet-from' action='/pet/action/' method='post'>")
                .append("<input type='hidden' name='action' value='' />");

        result.append("<div class='control_bar_pet'><ul>");
                if (!PetAction.getMap().isEmpty()) {
                    for(Map.Entry<PetAction, String> entry: PetAction.getMap().entrySet()) {
                        result.append("<li>").append("<div class='service-action-pet-btn' data-action='"+entry.getKey().name()+"'>"+entry.getValue()+"</div>").append("</li>");
                    }
                }
        result.append("</ul></div>");

        result.append("</form>");

        return result.toString();
    }
}
