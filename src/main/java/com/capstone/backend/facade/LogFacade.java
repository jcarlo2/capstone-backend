package com.capstone.backend.facade;

import com.capstone.backend.entity.Log;
import com.capstone.backend.service.LogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogFacade {

    private final LogService service;

    public LogFacade(LogService service) {
        this.service = service;
    }

    public List<Log> findAllLogs() {
        return service.findAllLogs();
    }

    public void saveLogRecord(Log log) {
        service.saveLogRecord(log);
    }
}
