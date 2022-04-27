package com.rud.app.controller;

import com.rud.app.DAO.RoleDao;
import com.rud.app.DAO.UserDao;
import com.rud.app.model.User;
import com.rud.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleDao roleDao;
    private final UserDao userDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao, UserDao userDao) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("users", userService.index());
        return "index";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "getUser";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", userService.getAll());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleDao.getAll());
        return "edit";
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute("user") User user, long id, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        user.setRoles(roleDao.getSetOfRoles(checkBoxRoles));
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
