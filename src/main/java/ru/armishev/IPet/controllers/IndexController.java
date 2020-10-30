package ru.armishev.IPet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.armishev.IPet.entity.pet.IPet;

/*
Главная страница
 */
@Controller
public class IndexController {
    @Autowired
    private IPet pet;

    @GetMapping("/")
    public String index(Model model) {
        if (pet.getId() > 0 && (!pet.isEscaped())) {
            return "redirect:/pet/";
        }

        return "index.html";
    }
}
