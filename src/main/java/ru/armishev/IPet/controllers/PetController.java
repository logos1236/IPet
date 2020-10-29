package ru.armishev.IPet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/create/")
    public String create(Model model) {
        pet.birth();
        pet.setName("Mailo");

        IPetView view = new PetView(pet);
        model.addAttribute("htmlPet", view.getHtml());

        return "pet/index.html";
    }

    @PostMapping("/action/")
    @ResponseBody
    public String action(HttpServletRequest request, Model model) {
        String action = request.getParameter("action");

        switch (action) {
            case "feed": {
                pet.eat(10);
                break;
            }
            case "play": {
                pet.play();
                break;
            }
        }

        IPetView view = new PetView(pet);
        model.addAttribute("htmlPet", view.getHtml());

        return "pet/index.html";
    }
}
