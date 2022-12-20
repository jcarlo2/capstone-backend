package com.capstone.backend.repository;

import com.capstone.backend.entity.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log,String> {
    List<Log> findAllByOrderByTimestampDesc();
    List<Log> findAllByIsDeletable(String isDeletable);

    @Transactional @Modifying
    void removeAllByIsDeletable(String isDeletable);
}
