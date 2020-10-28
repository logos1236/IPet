package ru.armishev.IPet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.user.IUser;

@Controller
@RequestMapping(value = "/pet")
public class PetController {
    @Autowired
    private IPet pet;

    @Autowired
    private IUser user;

    @GetMapping("/")
    public String add(Model model) {
        pet.birth();
        System.out.println(pet);


        pet.play();
        System.out.println(pet);

        pet.eat(23);
        System.out.println(pet);

        pet.birth();

        return "pet/index.html";
    }
}
