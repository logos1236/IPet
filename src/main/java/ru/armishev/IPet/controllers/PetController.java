package ru.armishev.IPet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.armishev.IPet.entity.event.Starvation;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.universe.IUniverse;
import ru.armishev.IPet.entity.user.IUser;

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
        /*pet.play();
        System.out.println(pet);

        pet.eat(23);
        System.out.println(pet);

        pet.birth();*/

        //Starvation starvation = new Starvation(pet);
        //starvation.execute(Instant.now().getEpochSecond());
        //System.out.println(pet);

        universe.timeMachine(pet);
        System.out.println(pet);

        return "pet/index.html";
    }

    @GetMapping("/create/")
    public String create(Model model) {
        pet.birth();
        System.out.println(pet);

        return "pet/index.html";
    }
}
