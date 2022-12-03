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
    MerchandiseDiscount findByIdAndQuantity(String id, Integer quantity);
    boolean existsByIdAndQuantity(String id, Integer quantity);
    Integer countAllByIdAndQuantityAndDiscount(String id, Integer quantity, Double discount);

    @Transactional @Modifying
    void deleteAllByIdAndQuantityAndDiscount(String id, Integer quantity, Double discount);

    @Transactional @Modifying
    void deleteAllByIdAndQuantity(String id, Integer quantity);

    @Transactional @Modifying
    @Query(value = "UPDATE product_discount SET discount = ?1, is_valid = 1 WHERE id = ?2 AND quantity = ?3", nativeQuery = true)
    void updateDiscount(Double discount, String id, Integer quantity);

    @Transactional @Modifying
    @Query(value = "UPDATE product_discount SET discount = ?5, is_valid = 1, quantity = ?4 WHERE id = ?3 AND quantity = ?1 AND discount = ?2", nativeQuery = true)
    void updateDiscountAndQuantity(Double discount, Integer quantity, String id, Integer quantityUpdate, Double discountUpdate);

    @Transactional @Modifying
    @Query(value = "DELETE FROM product_discount WHERE id = ?1 AND quantity = ?2", nativeQuery = true)
    void archiveProductDiscount(String id, Integer quantity);
}
