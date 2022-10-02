package com.capstone.backend.repository;

import com.capstone.backend.entity.ProductDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DiscountRepository extends CrudRepository<ProductDiscount, Double> {

    @Query(value = "SELECT * FROM product_discount WHERE id = ?1 AND quantity <= ?2 ORDER BY discount DESC LIMIT 1 ", nativeQuery = true)
    ProductDiscount discount(String id, BigDecimal quantity);
}
