package com.capstone.backend.repository;

import com.capstone.backend.entity.MerchandiseDiscount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MerchandiseDiscountRepository extends CrudRepository<MerchandiseDiscount, String> {

    @Query(value = "SELECT * FROM product_discount WHERE id = ?1 AND quantity <= ?2 ORDER BY discount DESC LIMIT 1 ", nativeQuery = true)
    MerchandiseDiscount discount(String id, BigDecimal quantity);

    List<MerchandiseDiscount> findAllByIdAndIsValidOrderByDiscount(String id, String isValid);

    boolean existsByIdAndQuantity(String id, Integer quantity);

    @Transactional @Modifying
    @Query(value = "UPDATE product_discount SET discount = ?1, is_valid = 1 WHERE id = ?2 AND quantity = ?3", nativeQuery = true)
    void updateDiscount(Double discount, String id, Integer quantity);
}
