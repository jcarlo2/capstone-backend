package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionReport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionReportRepository extends CrudRepository<TransactionReport, String> {

    boolean existsByIdAndIsValid(String id, String isValid);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 1 WHERE id = ?1",nativeQuery = true)
    void validate(String id);

    List<TransactionReport> findByIsValidOrderByTimestampDesc(String isValid);

    List<TransactionReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid, String start);

    List<TransactionReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String end);

    List<TransactionReport> findAllByIdContainsAndIsValidOrderByTimestampDesc(String search, String isValid);

    List<TransactionReport> findAllByIsValidAndTimestampBetween(String isValid, String start, String end);

    List<TransactionReport> findAllByIsValidOrderByTimestampDesc(String isValid);

}
