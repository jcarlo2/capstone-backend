package com.capstone.backend.facade;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.MerchandiseDiscount;
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

    public List<Merchandise> findMerchandiseBySearch(String search) {
        return product.findMerchandiseBySearch(search);
    }

    public void updateProductQuantity(Integer quantity, String id) {
        product.updateProductQuantity(quantity,id);
    }

    public List<MerchandiseDiscount> findAllValidDiscount(String id) {
        return product.findAllValidDiscount(id);
    }

    public List<MerchandiseDiscount> findAllInvalidDiscount(String id) {
        return product.findAllInvalidDiscount(id);
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
}
