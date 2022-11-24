package com.capstone.backend.service;

import com.capstone.backend.entity.Log;
import com.capstone.backend.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAllLogs() {
        return logRepository.findAllByOrderByTimestampDesc();
    }

    public void saveLogRecord(Log log) {
        logRepository.save(log);
    }
}
