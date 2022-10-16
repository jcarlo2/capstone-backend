package com.capstone.backend.facade;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.ProductDiscount;
import com.capstone.backend.service.MerchandiseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductFacade {

    private final MerchandiseService product;

    public ProductFacade(MerchandiseService product) {
        this.product = product;
    }

    public ProductDiscount discount(String id, BigDecimal quantity) {
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
}
