package com.capstone.backend.controller;

import com.capstone.backend.entity.User;
import com.capstone.backend.facade.UserFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserFacade facade;

    public UserController(UserFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/verify")
    public boolean verifyUser(@RequestParam String username, @RequestParam String password) {
        return facade.verifyUser(username,password);
    }

    @GetMapping("/get-role")
    public int getRole(@RequestParam String username, @RequestParam String password) {
        return facade.getRole(username,password);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody User user) {
        return facade.create(user);
    }

    @GetMapping("/generate")
    public int idGenerator() {
        return facade.idGenerator();
    }

    @GetMapping("/get-user-list")
    public List<User> getUserList() {
        return facade.getUserList();
    }

    @PostMapping("/change-password")
    public boolean changePassword(String id, String oldPassword, String newPassword) {
        return facade.changePassword(id,oldPassword,newPassword);
    }

    @PostMapping("/delete-account")
    public boolean deleteAccount(String id, String password) {
        return facade.deleteAccount(id,password);
    }
}

