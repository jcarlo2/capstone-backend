package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionReport;
import com.capstone.backend.entity.TransactionReportHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionReportHistoryRepository extends CrudRepository<TransactionReportHistory, String> {
    List<TransactionReportHistory> findAllByOrderByTimestampDesc();

    List<TransactionReportHistory> findAllByTimestampBetweenOrderByTimestampDesc(String start, String end);

    List<TransactionReportHistory> findAllByTimestampLessThanEqualOrderByTimestampDesc(String end);

    List<TransactionReportHistory> findAllByTimestampGreaterThanEqualOrderByTimestampDesc(String start);

    List<TransactionReportHistory> findAllByIdContainsOrderByTimestampDesc(String search);
}
