package com.capstone.backend.service;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.ProductDiscount;
import com.capstone.backend.repository.DiscountRepository;
import com.capstone.backend.repository.MerchandiseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchandiseService {
    private final MerchandiseRepository productRepository;
    private final DiscountRepository discountRepository;

    public MerchandiseService(MerchandiseRepository productRepository, DiscountRepository discountRepository) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    public List<Merchandise> getAllMerchandise() {
        return productRepository.findAllByOrderByQuantityPerPiecesDesc();
    }

    public List<Merchandise> findMerchandiseBySearch(String search) {
        List<Merchandise> list = productRepository.findAllByOrderByQuantityPerPiecesDesc();
        return contains(search,list);
    }

    public List<Merchandise> contains(String search, @NotNull List<Merchandise> list) {
        search = search.toLowerCase();
        List<Merchandise> newList = new ArrayList<>();
            for(Merchandise merchandise : list) {
                if(merchandise.getId().toLowerCase().contains(search)
                || merchandise.getName().toLowerCase().contains(search)
                || merchandise.getPrice().toLowerCase().contains(search)) {
                    newList.add(merchandise);
                }
            }
        return newList;
    }

    public ProductDiscount discount(String id, BigDecimal quantity) {
        return discountRepository.discount(id,quantity);
    }

    public boolean hasStock(String id, Integer quantity) {
        final Optional<Merchandise> merch = productRepository.findById(id);
        if(merch.isEmpty() || quantity < 1) return false;
        return Integer.parseInt(merch.get().getQuantityPerPieces()) >= quantity;
    }

    public void updateProductQuantity(Integer quantity, String id) {
        productRepository.updateProductQuantity(quantity,id);
    }
}
