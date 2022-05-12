package com.rud.app.controller;

import com.rud.app.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class MainController {


    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model) {
        return "allUsers";
    }

    @GetMapping(value = "/users")
    public String user() {
        return "userPage";
    }


    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "select_roles", required = false) Model model ) {
        return "redirect:/admin";
    }
}
