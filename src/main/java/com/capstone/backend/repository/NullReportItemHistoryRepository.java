package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReportItemHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NullReportItemHistoryRepository extends CrudRepository<NullReportItemHistory, String> {
    List<NullReportItemHistory> findAllByReportIdAndTimestamp(String id, String timestamp);
}
