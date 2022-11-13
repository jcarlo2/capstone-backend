package com.capstone.backend.repository;

import com.capstone.backend.entity.MerchandiseDiscount;
import com.capstone.backend.entity.MerchandiseDiscountHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseDiscountHistoryRepository extends CrudRepository<MerchandiseDiscountHistory, String> {
    List<MerchandiseDiscountHistory> findAllByIdOrderByTimestampDesc(String id);
}
