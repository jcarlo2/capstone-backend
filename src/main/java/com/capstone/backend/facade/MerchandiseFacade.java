package com.capstone.backend.facade;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.MerchandiseDiscount;
import com.capstone.backend.entity.MerchandiseDiscountHistory;
import com.capstone.backend.entity.MerchandiseHistory;
import com.capstone.backend.service.MerchandiseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MerchandiseFacade {

    private final MerchandiseService product;

    public MerchandiseFacade(MerchandiseService product) {
        this.product = product;
    }

    public MerchandiseDiscount discount(String id, BigDecimal quantity) {
        return product.discount(id,quantity);
    }

    public boolean hasStock(String id, Integer quantity) {
        return product.hasStock(id,quantity);
    }

    public List<Merchandise> getAllMerchandise(String filter) {
        return product.getAllMerchandise(filter);
    }

    public List<Merchandise> findMerchandiseBySearch(String search, String filter) {
        return product.findMerchandiseBySearch(search,filter);
    }

    public void updateProductQuantity(Integer quantity, String id) {
        product.updateProductQuantity(quantity,id);
    }

    public List<MerchandiseDiscount> findAllValidDiscount(String id) {
        return product.findAllValidDiscount(id);
    }

    public List<MerchandiseDiscountHistory> findAllArchivedDiscountById(String id) {
        return product.findAllArchivedDiscountById(id);
    }

    public List<MerchandiseHistory> getAllProductHistory(String id) {
        return product.getAllProductHistory(id);
    }

    public Merchandise findProductById(String id) {
        return product.findProductById(id);
    }

    public boolean isProductExist(String id) {
        return product.isProductExist(id);
    }

    public void addProduct(Merchandise merchandise) {
        product.addProduct(merchandise);
    }

    public void updateProduct(Merchandise merchandise) {
        product.updateProduct(merchandise);
    }

    public void archiveProduct(String id) {
        product.archiveProduct(id);
    }

    public void unarchiveProduct(String id) {
        product.unarchiveProduct(id);
    }

    public void addProductDiscount(Integer quantity, Double discount, String id) {
        product.addProductDiscount(quantity,discount,id);
    }

    public boolean checkIfDiscountQuantityExist(String id, Integer quantity) {
        return product.checkIfDiscountQuantityExist(id,quantity);
    }

    public void archiveProductDiscount(String id, Integer quantity) {
        product.archiveProductDiscount(id,quantity);
    }

    public List<Merchandise> findAllInactiveProduct() {
        return product.findAllInactiveProduct();
    }

    public void activateProduct(String id, boolean isZero) {
        product.activateProduct(id,isZero);
    }

    public boolean updateProductDiscount(String id, Integer quantity, Double discount, Integer quantityUpdate, Double discountUpdate) {
        return product.updateProductDiscount(id,quantity,discount,quantityUpdate,discountUpdate);
    }

    public boolean isMerchandiseDiscountExist(String id, Integer quantity) {
        return product.isMerchandiseDiscountExist(id,quantity);
    }

    public void activateDiscount(String id, Integer quantity, Double discount, boolean isOverride) {
        product.activateDiscount(id,quantity,discount,isOverride);
    }
}
