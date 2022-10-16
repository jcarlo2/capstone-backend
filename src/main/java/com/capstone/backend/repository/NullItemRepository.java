package com.capstone.backend.repository;

import com.capstone.backend.entity.NullReportItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NullItemRepository extends CrudRepository<NullReportItem,String> {
    Boolean existsByReportId(String report);

    List<NullReportItem> findAllByReportId(String id);

    @Modifying @Transactional
    void deleteNullReportItemByReportIdAndId(String reportId, String id);

    @Modifying @Transactional
    @Query(value = "UPDATE null_item SET quantity = ?1, discount = ?2, total_amount = ?3 WHERE report_id = ?4 AND id = ?5",nativeQuery = true)
    void updateByReportIdAndId(Integer quantity, String discount, String total, String reportId, String productId);

    List<NullReportItem> findAllByReportIdOrderByQuantityDesc(String id);
}
