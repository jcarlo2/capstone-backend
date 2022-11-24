package com.capstone.backend.controller;

import com.capstone.backend.facade.DateFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/date")
public class DateController {
    private final DateFacade facade;

    public DateController(DateFacade facade) {
        this.facade = facade;
    }

    @GetMapping("get-date")
    public String getDate() {
        return facade.getDate();
    }
}
