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
    List<Merchandise> findAllByIsActiveOrderById(String isActive);
    List<Merchandise> findAllByIsActiveOrderByName(String isActive);
    List<Merchandise> findAllByIsActiveOrderByQuantityPerPiecesDesc(String isActive);
    List<Merchandise> findAllByIsActiveOrderByQuantityPerPieces(String isActive);
    List<Merchandise> findAllByIsActiveOrderByPrice(String isActive);
    List<Merchandise> findAllByIsActiveOrderByPriceDesc(String isActive);

    List<Merchandise> findAllByPriceLessThanEqualAndIsActiveOrderByPriceDesc(String search,String isActive);

    @Modifying @Transactional
    @Query(value = "UPDATE product SET quantity_per_pieces = product.quantity_per_pieces + ?1 WHERE product.id = ?2",nativeQuery = true)
    void updateProductQuantity(Integer quantity, String id);

    @Modifying @Transactional
    @Query(value = "UPDATE product SET name = ?2, price = ?3, capital = ?4 WHERE product.id = ?1",nativeQuery = true)
    void updateProduct(String id, String name, String price, String capital);



    @Transactional @Modifying
    @Query(value = "UPDATE product SET is_active = ?2 WHERE id = ?1",nativeQuery = true)
    void archiveProduct(String id, String isActive);
}
