package com.capstone.backend.service;

import com.capstone.backend.entity.User;
import com.capstone.backend.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    private final UserRepository user;

    public UserService(UserRepository user) {
        this.user = user;
    }

    public boolean existsUserByIdAndPassword(String username, @NotNull String password) {
        String pass = Base64.getEncoder().encodeToString(password.getBytes());
        return user.existsUserByIdAndPassword(username,pass);
    }

    public int getRole(String username, @NotNull String password) {
        String pass = Base64.getEncoder().encodeToString(password.getBytes());
        return user.findByIdAndPassword(username,pass).getRole();
    }

    public boolean create(@NotNull User newUser) {
        String password = Base64.getEncoder().encodeToString(newUser.getPassword().getBytes());
        newUser.setPassword(password);
        if(newUser.getFirstName().equalsIgnoreCase("admin") ||
            newUser.getLastName().equalsIgnoreCase("admin") ||
            (!newUser.isSave() && user.existsByFirstNameAndLastName(newUser.getFirstName(), newUser.getLastName()))) return false;
        user.save(newUser);
        return true;
    }

    public int idGenerator() {
        int max = 999999;
        int min = 100000;
        int id = (int) (Math.floor(Math.random() * (max - min + 1)) + min);
        while(user.existsById(String.valueOf(id))) {
            id = (int) (Math.floor(Math.random() * (max - min + 1)) + min);
        }
        return  id;
    }

    public List<User> getUserList() {
        return user.findAllByIdNotContainsAndFirstNameNotContainingAndLastNameNotContaining("7777","admin","admin");
    }

    public boolean changePassword(@NotNull String id, @NotNull String oldPassword, @NotNull String newPassword) {
        String newPass = Base64.getEncoder().encodeToString(newPassword.getBytes());
        String oldPass = Base64.getEncoder().encodeToString(oldPassword.getBytes());
        if(!user.existsUserByIdAndPassword(id,oldPass)) return false;
        user.changePassword(id,oldPass,newPass);
        return true;
    }

    public boolean deleteAccount(String id, @NotNull String password) {
        String adminPassword = Base64.getEncoder().encodeToString(password.getBytes());
        if(user.existsUserByIdAndPassword("7777",adminPassword) && user.existsById(id)) {
            user.removeById(id);
            return true;
        }
        return false;
    }
}
