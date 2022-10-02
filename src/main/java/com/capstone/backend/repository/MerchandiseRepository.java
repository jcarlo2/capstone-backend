package com.capstone.backend.repository;

import com.capstone.backend.entity.Merchandise;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MerchandiseRepository extends CrudRepository<Merchandise, String> {
    List<Merchandise> findAllByOrderByQuantityPerPiecesDesc();

    @Modifying @Transactional
    @Query(value = "UPDATE product SET quantity_per_pieces = product.quantity_per_pieces + ?1 WHERE product.id = ?2",nativeQuery = true)
    void updateProductQuantity(Integer quantity, String id);
}
