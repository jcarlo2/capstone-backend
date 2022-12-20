package com.capstone.backend.repository;

import com.capstone.backend.entity.LogHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHistoryRepository extends CrudRepository<LogHistory,String> {
}
