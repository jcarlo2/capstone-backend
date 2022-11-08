package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NullReportRepository extends CrudRepository<NullReport,String> {
    NullReport findByLink(String link);
    boolean existsByLink(String link);
    boolean existsByIdAndIsValid(String link, String isValid);
    List<NullReport> findAllByIsValidOrderByTimestampDesc(String isValid);
    List<NullReport> findAllByIsValidAndTimestampBetweenOrderByTimestampDesc(String isValid, String start, String end);
    List<NullReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String end);
    List<NullReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid, String start);
    List<NullReport> findAllByIsValidAndIdContainsOrIsValidAndLinkContainsOrderByTimestampDesc(String isValid, String id, String valid, String link);

    @Transactional
    @Modifying
    @Query(value = "UPDATE null_report SET is_valid = 0 WHERE link = ?1",nativeQuery = true)
    void invalidateNullReportByLink(String link);

    @Transactional
    @Modifying
    @Query(value = "UPDATE null_report SET is_valid = 1 WHERE link = ?1",nativeQuery = true)
    void validateNullReportByLink(String link);


    @Transactional
    @Modifying
    @Query(value = "UPDATE null_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidateNullReportById(String id);

}
