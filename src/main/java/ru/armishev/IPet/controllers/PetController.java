package ru.armishev.IPet.controllers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.armishev.IPet.entity.action.PetAction;
import ru.armishev.IPet.entity.event.Starvation;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.universe.IUniverse;
import ru.armishev.IPet.entity.user.IUser;
import ru.armishev.IPet.views.IPetView;
import ru.armishev.IPet.views.PetView;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Controller
@RequestMapping(value = "/pet")
public class PetController {
    @Autowired
    private IPet pet;

    @Autowired
    private IUser user;

    @Autowired
    private IUniverse universe;

    @GetMapping("/")
    public String index(Model model) {
        universe.timeMachine(pet);

        IPetView view = new PetView(pet);
        model.addAttribute("htmlPet", view.getHtml());
        model.addAttribute("htmlPetControlPanel", view.getHtmlControlPanel());

        return "pet/index.html";
    }

    @PostMapping("/")
    @ResponseBody
    public String indexJson() {
        JsonObject result = new JsonObject();
        IPetView view = new PetView(pet);
        universe.timeMachine(pet);

        result.addProperty("htmlPetControlPanel", view.getHtmlControlPanel());
        result.addProperty("htmlPet", view.getHtml());


        return result.toString();
    }

    @GetMapping("/create/")
    public String create(Model model) {
        pet.birth();
        pet.setName("Mailo");

        IPetView view = new PetView(pet);
        model.addAttribute("htmlPet", view.getHtml());

        return "pet/index.html";
    }

    @PostMapping(value = "/action/", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String action(HttpServletRequest request, Model model) {
        IPetView view = new PetView(pet);
        JsonObject result = new JsonObject();
        result.addProperty("success", false);
        result.addProperty("success_message", "Непонятная команда");

        String action = request.getParameter("action");
        if (PetAction.valueOf(action).equals(PetAction.FEED)) {
            pet.eat(10);
            result.addProperty("success", true);
            result.addProperty("success_message", "Успешно покормлен");
        }
        if (PetAction.valueOf(action).equals(PetAction.PLAY)) {
            pet.play();
            result.addProperty("success", true);
            result.addProperty("success_message", "Успешно поиграл");
        }

        result.addProperty("htmlPetControlPanel", view.getHtmlControlPanel());
        result.addProperty("htmlPet", view.getHtml());

        return result.toString();
    }
}
