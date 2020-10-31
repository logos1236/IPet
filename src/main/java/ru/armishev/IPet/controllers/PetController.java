package ru.armishev.IPet.controllers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.armishev.IPet.dao.log.LogPetActionDAO;
import ru.armishev.IPet.dao.log.LogRepository;
import ru.armishev.IPet.entity.action.PetAction;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.universe.IUniverse;
import ru.armishev.IPet.views.ILogView;
import ru.armishev.IPet.views.IPetView;
import ru.armishev.IPet.views.LogView;
import ru.armishev.IPet.views.PetView;

import javax.servlet.http.HttpServletRequest;

/*
Раздел с питомцем
*/

@Controller
@RequestMapping(value = "/pet")
public class PetController {
    @Autowired
    private IPet pet;

    @Autowired
    private IUniverse universe;

    @Autowired
    private LogRepository logRepository;

    /*
    Страница с информацией о питомце
    */
    @GetMapping("/")
    public String index(Model model) {
        if (pet.getId() == 0) {
            return "redirect:/";
        }

        universe.timeMachine();

        IPetView view = new PetView(pet);
        ILogView logView = new LogView(logRepository, pet);

        model.addAttribute("htmlPet", view.getHtml());
        model.addAttribute("htmlPetControlPanel", view.getHtmlControlPanel());
        model.addAttribute("htmlPetLife", logView.getPetLifeHtml());

        return "pet/index.html";
    }

    /*
    Ajax с информацией о питомце
    */
    @PostMapping("/")
    @ResponseBody
    public String indexJson() {
        JsonObject result = new JsonObject();
        IPetView view = new PetView(pet);
        ILogView logView = new LogView(logRepository, pet);

        universe.timeMachine();

        result.addProperty("htmlPetControlPanel", view.getHtmlControlPanel());
        result.addProperty("htmlPet", view.getHtml());
        result.addProperty("htmlPetLife", logView.getPetLifeHtml());

        return result.toString();
    }

    /*
    Создать питомца
    */
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

    /*
    Получить сохраненного питомца из базы
    */
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

    /*
    События, которые пользователь генерирует для питомца
    */
    @PostMapping(value = "/action/", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String action(HttpServletRequest request) {
        IPetView view = new PetView(pet);
        long current_time = System.currentTimeMillis();

        JsonObject result = new JsonObject();
        result.addProperty("success", false);
        result.addProperty("error_message", "Непонятная команда");

        universe.timeMachine();

        pet.loadFromDAO(pet.getId());

        String action = request.getParameter("action");
        if (PetAction.valueOf(action).equals(PetAction.FEED)) {
            pet.eat(10);

            logRepository.save(new LogPetActionDAO(pet.getId(), current_time, pet.getName()+" покормлен"));

            result.addProperty("success", true);
            result.addProperty("success_message", "Успешно покормлен");
        }
        if (PetAction.valueOf(action).equals(PetAction.PLAY)) {
            pet.play();

            logRepository.save(new LogPetActionDAO(pet.getId(), current_time, pet.getName()+" поиграл c хозяином"));

            result.addProperty("success", true);
            result.addProperty("success_message", "Успешно поиграл");
        }

        pet.saveToDAO();

        result.addProperty("htmlPetControlPanel", view.getHtmlControlPanel());
        result.addProperty("htmlPet", view.getHtml());

        return result.toString();
    }
}
