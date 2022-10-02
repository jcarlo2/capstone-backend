package com.capstone.backend.facade;

import com.capstone.backend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class LoginFacade {
    private final UserService service;

    public LoginFacade(UserService service) {
        this.service = service;
    }

    public boolean verifyUser(String username, String password) {
        return service.existsUserByIdAndPassword(username,password);
    }
}
