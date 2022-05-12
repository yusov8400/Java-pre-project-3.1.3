package com.rud.app.controller;

import com.rud.app.model.User;
import com.rud.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping("/all")
    public ResponseEntity<List<User>> list() {
        List<User> users = userService.index();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/newUser")
    public ResponseEntity<String> save(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
