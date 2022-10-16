package com.capstone.backend.repository;

import com.capstone.backend.entity.DeliveryItemDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryItemRepository extends CrudRepository<DeliveryItemDetail,String> {
    List<DeliveryItemDetail> findAllByUniqueIdOrderByQuantity(String id);
}
