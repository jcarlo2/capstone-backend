package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionItemDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionItemRepository extends CrudRepository<TransactionItemDetail,Long> {
    List<TransactionItemDetail> findAllByUniqueId(String id);

    Boolean existsByUniqueId(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM transaction_report_item WHERE unique_id = ?1",nativeQuery = true)
    void deleteItems(String id);
}
