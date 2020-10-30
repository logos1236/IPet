package ru.armishev.IPet.controllers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.armishev.IPet.dao.LogRepository;
import ru.armishev.IPet.entity.action.PetAction;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.universe.IUniverse;
import ru.armishev.IPet.entity.user.IUser;
import ru.armishev.IPet.views.ILogView;
import ru.armishev.IPet.views.IPetView;
import ru.armishev.IPet.views.LogView;
import ru.armishev.IPet.views.PetView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/pet")
public class PetController {
    @Autowired
    private IPet pet;

    @Autowired
    private IUser user;

    @Autowired
    private IUniverse universe;

    @Autowired
    private LogRepository logRepository;

    @GetMapping("/")
    public String index(Model model) {
        if (pet.getId() == 0) {
            return "redirect:/";
        }

        universe.timeMachine(pet);

        IPetView view = new PetView(pet);
        ILogView logView = new LogView(logRepository, pet);

        model.addAttribute("htmlPet", view.getHtml());
        model.addAttribute("htmlPetControlPanel", view.getHtmlControlPanel());
        model.addAttribute("htmlPetLife", logView.getPetLifeHtml());

        return "pet/index.html";
    }

    @PostMapping("/")
    @ResponseBody
    public String indexJson() {
        JsonObject result = new JsonObject();
        IPetView view = new PetView(pet);
        ILogView logView = new LogView(logRepository, pet);

        universe.timeMachine(pet);

        result.addProperty("htmlPetControlPanel", view.getHtmlControlPanel());
        result.addProperty("htmlPet", view.getHtml());
        result.addProperty("htmlPetLife", logView.getPetLifeHtml());

        return result.toString();
    }

    @PostMapping("/create/")
    @ResponseBody
    public String create(HttpServletRequest request) {
        String pet_name = request.getParameter("name");
        JsonObject result = new JsonObject();

        try {
            if ((pet.getId() > 0) && (!pet.isEscaped())) {
                throw new Exception("Питомец уже создан");
            }
            if ((pet_name.equals(""))) {
                throw new Exception("Имя питомца пусто");
            }

            pet.birth(pet_name);

            result.addProperty("success", true);
            result.addProperty("success_message", "Питомец успешно создан");
            result.addProperty("redirect_url", "/pet/");
        } catch (Exception e) {
            result.addProperty("success", false);
            result.addProperty("error_message", e.getMessage());
        }

        return result.toString();
    }

    @PostMapping("/get-existing/")
    @ResponseBody
    public String getExisting(HttpServletRequest request) {
        JsonObject result = new JsonObject();
        Long pet_id;

        try {
            try {
                pet_id = Long.valueOf(request.getParameter("pet_id"));
            } catch (Exception e) {
                throw new Exception("id питомца не задано");
            }
            if ((pet_id < 0 || pet_id == null)) {
                throw new Exception("id питомца не задано");
            }

            pet.loadFromDAO(pet_id);

            result.addProperty("success", true);
            result.addProperty("success_message", "Питомец успешно загружен");
            result.addProperty("redirect_url", "/pet/");
        } catch (Exception e) {
            result.addProperty("success", false);
            result.addProperty("error_message", e.getMessage());
        }

        return result.toString();
    }

    @PostMapping(value = "/action/", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String action(HttpServletRequest request) {
        IPetView view = new PetView(pet);
        JsonObject result = new JsonObject();
        result.addProperty("success", false);
        result.addProperty("error_message", "Непонятная команда");

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
