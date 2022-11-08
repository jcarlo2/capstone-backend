package com.capstone.backend.repository;

import com.capstone.backend.entity.DeliveryReport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeliveryReportRepository extends CrudRepository<DeliveryReport,String> {
    List<DeliveryReport> findAllByIsValidOrderByTimestampDesc(String isValid);
    List<DeliveryReport> findAllByIsValidAndTimestampBetweenOrderByTimestampDesc(String isValid, String start, String end);
    List<DeliveryReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String end);
    List<DeliveryReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid, String start);
    List<DeliveryReport> findAllByIsValidAndIdContainsOrIsValidAndLinkContainsOrderByTimestampDesc(String isValid, String search,String isValid1, String search1);

    @Transactional @Modifying
    @Query(value = "UPDATE product_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void archivedDelivery(String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product_report SET is_valid = 0 WHERE link = ?1",nativeQuery = true)
    void invalidateDeliveryReportByLink(String link);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product_report SET is_valid = 1 WHERE link = ?1",nativeQuery = true)
    void validateDeliveryReportByLink(String link);

    boolean existsByLink(String link);

    DeliveryReport findByLink(String link);
}
