package com.capstone.backend.service;

import com.capstone.backend.entity.Log;
import com.capstone.backend.entity.LogHistory;
import com.capstone.backend.repository.LogHistoryRepository;
import com.capstone.backend.repository.LogRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final LogHistoryRepository logHistoryRepository;

    public LogService(LogRepository logRepository, LogHistoryRepository logHistoryRepository) {
        this.logRepository = logRepository;
        this.logHistoryRepository = logHistoryRepository;
    }

    public List<Log> findAllLogs() {
        return logRepository.findAllByOrderByTimestampDesc();
    }

    public void saveLogRecord(Log log) {
        logRepository.save(log);
    }

    public void archiveAll() {
        List<LogHistory> logHistoryList = convertLogToLogHistory(logRepository.findAllByIsDeletable("1"));
        logHistoryRepository.saveAll(logHistoryList);
        logRepository.removeAllByIsDeletable("1");
        logRepository.save(new Log(
            "",
            "100000",
            "Archived All",
            "Archived all logs",
            "",
            "0"
        ));
    }

    private @NotNull List<LogHistory> convertLogToLogHistory(@NotNull List<Log> logList) {
        List<LogHistory> logHistoryList = new ArrayList<>();
        logList.forEach(log -> {
            logHistoryList.add(new LogHistory(
                    "",
                    log.getUser(),
                    log.getAction(),
                    log.getDescription(),
                    log.getTimestamp(),
                    ""
            ));
        });
        return logHistoryList;
    }
}
