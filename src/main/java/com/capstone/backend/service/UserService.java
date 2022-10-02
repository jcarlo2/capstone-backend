package com.capstone.backend.service;

import com.capstone.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository user;

    public UserService(UserRepository user) {
        this.user = user;
    }

    public boolean existsUserByIdAndPassword(String username, String password) {
        return user.existsUserByIdAndPassword(username,password);
    }
}
