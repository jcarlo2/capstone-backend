package com.capstone.backend.repository;

import com.capstone.backend.entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log,String> {
    List<Log> findAllByOrderByTimestampDesc();
}
