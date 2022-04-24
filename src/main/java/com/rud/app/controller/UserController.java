package com.rud.app.controller;

import com.rud.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/users")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "getUser";
    }

    @GetMapping("/login")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
