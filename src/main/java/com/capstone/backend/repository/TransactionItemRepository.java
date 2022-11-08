package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionReportItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionItemRepository extends CrudRepository<TransactionReportItem,Long> {
    List<TransactionReportItem> findAllByUniqueId(String id);

    Boolean existsByUniqueId(String id);
    Boolean existsByUniqueIdAndProductId(String uniqueId, String productId);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report_item SET sold = ?1, sold_total = ?2, discount_percentage = ?3, total_amount = ?4 WHERE unique_id =?5 AND prod_id = ?6",nativeQuery = true)
    void updateItem(Integer sold, String soldTotal, String discount, String totalAmount, String reportId, String productId);

    @Modifying @Transactional
    @Query(value = "DELETE FROM transaction_report_item WHERE unique_id = ?1",nativeQuery = true)
    void deleteAllByUniqueId(String uniqueId);
}
