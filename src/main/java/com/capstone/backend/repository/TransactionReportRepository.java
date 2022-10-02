package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionDetail;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionReportRepository extends CrudRepository<TransactionDetail, String> {

    boolean existsById(@NotNull String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 1 WHERE id = ?1",nativeQuery = true)
    void validate(String id);

    List<TransactionDetail> findByIsValidOrderByTimestampDesc(String isValid);

    List<TransactionDetail> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid, String start);

    List<TransactionDetail> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String end);

    List<TransactionDetail> findAllByIdContainsAndIsValid(String search,String isValid);

    List<TransactionDetail> findAllByIsValidAndTimestampBetween(String isValid,String start,String end);

    @Modifying @Transactional
    @Query(value = "DELETE FROM transaction_report WHERE id = ?1",nativeQuery = true)
    void deleteReport(String id);
}
