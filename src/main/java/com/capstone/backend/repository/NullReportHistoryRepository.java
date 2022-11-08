package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReportHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NullReportHistoryRepository extends CrudRepository<NullReportHistory,String> {
    List<NullReportHistory> findAllByOrderByTimestampDesc();
    List<NullReportHistory> findAllByTimestampBetweenOrderByTimestampDesc(String start, String end);

    List<NullReportHistory> findAllByIdContainsOrLinkContainsOrderByTimestampDesc(String search, String search1);

    List<NullReportHistory> findAllByTimestampGreaterThanEqualOrderByTimestampDesc(String start);

    List<NullReportHistory> findAllByTimestampLessThanEqualOrderByTimestampDesc(String end);

    boolean existsByIdAndTimestamp(String id, String timestamp);
}
