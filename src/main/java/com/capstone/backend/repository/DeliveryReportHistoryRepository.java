package com.capstone.backend.repository;

import com.capstone.backend.entity.DeliveryReportHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryReportHistoryRepository extends CrudRepository<DeliveryReportHistory, String> {
    List<DeliveryReportHistory> findAllByOrderByTimestampDesc();
    List<DeliveryReportHistory> findAllByTimestampBetweenOrderByTimestampDesc(String start, String end);

    List<DeliveryReportHistory> findAllByIdContainsOrLinkContainsOrderByTimestampDesc(String search, String search1);

    List<DeliveryReportHistory> findAllByTimestampGreaterThanEqualOrderByTimestampDesc(String start);

    List<DeliveryReportHistory> findAllByTimestampLessThanEqualOrderByTimestampDesc(String end);

    boolean existsByIdAndTimestamp(String id, String timestamp);
}
