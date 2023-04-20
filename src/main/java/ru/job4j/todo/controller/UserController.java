package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/signUpForm")
    public String getSignUpForm() {
        return "user/add";
    }

    @PostMapping("/addNew")
    public String addNewUser(Model model, @ModelAttribute User user) {
        var optionalUser = userRepository.save(user);
        if (optionalUser.isEmpty()) {
            model.addAttribute(
                    "message",
                    "An account already exists. Please use log in option");
            return "error/log";
        }
        return "redirect:/users/loginForm";
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute User user, HttpServletRequest req) {
        var optionalUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (optionalUser.isEmpty()) {
            model.addAttribute(
                    "message",
                    "Incorrect input details or sign up first");
            return "error/log";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", optionalUser.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/loginForm";
    }

}