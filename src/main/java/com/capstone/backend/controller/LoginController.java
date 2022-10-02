package com.capstone.backend.controller;

import com.capstone.backend.facade.LoginFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginFacade facade;

    public LoginController(LoginFacade facade) {
        this.facade = facade;
    }

    @GetMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> verifyUser(@RequestParam String username,
                                              @RequestParam String password) {
        return new ResponseEntity<>(facade.verifyUser(username,password), HttpStatus.OK);
    }
}
