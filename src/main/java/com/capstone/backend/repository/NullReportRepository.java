package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NullReportRepository extends CrudRepository<NullReport,String> {
}
