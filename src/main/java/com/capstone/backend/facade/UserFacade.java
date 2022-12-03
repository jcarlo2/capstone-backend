package com.capstone.backend.facade;

import com.capstone.backend.entity.User;
import com.capstone.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFacade {
    private final UserService service;

    public UserFacade(UserService service) {
        this.service = service;
    }

    public boolean verifyUser(String username, String password) {
        return service.existsUserByIdAndPassword(username,password);
    }

    public int getRole(String username, String password) {
        return service.getRole(username,password);
    }

    public boolean create(User user) {
        return service.create(user);
    }

    public int idGenerator() {
        return service.idGenerator();
    }

    public List<User> getUserList() {
        return service.getUserList();
    }

    public boolean changePassword(String id, String oldPassword, String newPassword) {
        return service.changePassword(id,oldPassword,newPassword);
    }

    public boolean deleteAccount(String id, String password) {
        return service.deleteAccount(id,password);
    }
}
