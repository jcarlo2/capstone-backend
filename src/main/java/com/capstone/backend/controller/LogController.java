package com.capstone.backend.controller;

import com.capstone.backend.entity.Log;
import com.capstone.backend.facade.LogFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class LogController {

    private final LogFacade facade;

    public LogController(LogFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/get-all-log")
    public List<Log> findAllLogs() {
        return facade.findAllLogs();
    }

    @PostMapping("/save-log-record")
    public void saveLogRecord(@RequestBody Log log) {
        facade.saveLogRecord(log);
    }
}
