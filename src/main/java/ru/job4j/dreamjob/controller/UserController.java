package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.services.UserService;

import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/failUser")
    public String fail(Model model) {
        return "failUser";
    }

    @GetMapping("/successUser")
    public String success(Model model) {
        return "successUser";
    }

    @GetMapping("/addUser")
    public String addUser() {
        return "addUser";
    }

    @PostMapping("/registrationUser")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "redirect:/failUser";
        }
        return "redirect:/successUser";
    }

    @PostMapping("/failRedirect")
    public String failRedirect(Model model) {
        return "redirect:/users";
    }

    @PostMapping("/successRedirect")
    public String successRedirect(Model model) {
        return "redirect:/users";
    }
}
