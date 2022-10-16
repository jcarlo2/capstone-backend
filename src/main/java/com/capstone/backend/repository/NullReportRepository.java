package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NullReportRepository extends CrudRepository<NullReport,String> {
    NullReport findByLink(String link);
    boolean existsByLink(String link);
    List<NullReport> findAllByIsValidOrderByTimestampDesc(String isValid);
    List<NullReport> findAllByIsValidAndTimestampBetweenOrderByTimestampDesc(String isValid, String start, String end);
    List<NullReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String end);
    List<NullReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid, String start);
    List<NullReport> findAllByIsValidAndIdContainsOrLinkContainsOrderByTimestampDesc(String isValid, String id, String link);
}
