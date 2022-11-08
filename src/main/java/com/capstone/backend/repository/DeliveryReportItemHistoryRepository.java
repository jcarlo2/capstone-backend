package com.capstone.backend.repository;

import com.capstone.backend.entity.DeliveryReportItemHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryReportItemHistoryRepository extends CrudRepository<DeliveryReportItemHistory, String> {
    List<DeliveryReportItemHistory> findAllByReportIdAndTimestamp(String id, String timestamp);
}
