package com.capstone.backend.repository;

import com.capstone.backend.entity.DeliveryReportItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeliveryReportItemRepository extends CrudRepository<DeliveryReportItem,String> {
    List<DeliveryReportItem> findAllByUniqueIdOrderByQuantity(String id);
    List<DeliveryReportItem> findAllByUniqueId(String id);

    @Transactional @Modifying
    @Query(value = "DELETE FROM product_item WHERE unique_id = ?1 ",nativeQuery = true)
    void deleteExistingItems(String id);
}
