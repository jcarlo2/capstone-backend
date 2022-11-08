package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionReportItem;
import com.capstone.backend.entity.TransactionReportItemHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionReportItemHistoryRepository extends CrudRepository<TransactionReportItemHistory, String> {
    List<TransactionReportItemHistory> findAllByReportIdAndTimestamp(String id, String timestamp);
    boolean existsByReportIdAndTimestamp(String id, String timestamp);
}
