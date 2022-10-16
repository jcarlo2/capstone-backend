package com.capstone.backend.repository;

import com.capstone.backend.entity.DeliveryDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDetailRepository extends CrudRepository<DeliveryDetail,String> {
    List<DeliveryDetail> findAllByIsValidOrderByTimestampDesc(String isValid);
    List<DeliveryDetail> findAllByIsValidAndTimestampBetweenOrderByTimestampDesc(String isValid,String start,String end);
    List<DeliveryDetail> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid,String end);
    List<DeliveryDetail> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid,String end);
    List<DeliveryDetail> findAllByIsValidAndIdContainsOrderByTimestampDesc(String isValid,String search);
}
