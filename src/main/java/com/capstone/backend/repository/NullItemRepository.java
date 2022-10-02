package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReportItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NullItemRepository extends CrudRepository<NullReportItem,String> {
    Boolean existsByReportId(String report);
}
