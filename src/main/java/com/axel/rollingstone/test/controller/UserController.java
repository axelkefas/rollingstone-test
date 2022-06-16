package com.axel.rollingstone.test.controller;

import com.axel.rollingstone.test.entity.User;
import com.axel.rollingstone.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/user")
    List<User> getUser() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user")
    void deleteUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PostMapping("/login")
    String login(@RequestBody User user) {
       return userService.login(user.getUsername(),user.getPassword());
    }


}
