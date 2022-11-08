package com.capstone.backend.service;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.MerchandiseDiscount;
import com.capstone.backend.entity.MerchandiseHistory;
import com.capstone.backend.repository.MerchandiseDiscountRepository;
import com.capstone.backend.repository.MerchandiseHistoryRepository;
import com.capstone.backend.repository.MerchandiseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseDiscountRepository merchandiseDiscountRepository;
    private final MerchandiseHistoryRepository merchandiseHistoryRepository;

    public MerchandiseService(MerchandiseRepository merchandiseRepository, MerchandiseDiscountRepository merchandiseDiscountRepository, MerchandiseHistoryRepository merchandiseHistoryRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.merchandiseDiscountRepository = merchandiseDiscountRepository;
        this.merchandiseHistoryRepository = merchandiseHistoryRepository;
    }

    public List<Merchandise> getAllMerchandise(@NotNull String filter) {
        if(filter.equalsIgnoreCase("Filter By Name")) return merchandiseRepository.findAllByIsActiveOrderByName("1");
        else if(filter.equalsIgnoreCase("Filter By Stock H-L")) return merchandiseRepository.findAllByIsActiveOrderByQuantityPerPiecesDesc("1");
        else if(filter.equalsIgnoreCase("Filter By Stock L-H")) return merchandiseRepository.findAllByIsActiveOrderByQuantityPerPieces("1");
        else if(filter.equalsIgnoreCase("Filter By Price H-L")) return merchandiseRepository.findAllByIsActiveOrderByPriceDesc("1");
        else if(filter.equalsIgnoreCase("Filter By Price L-H")) return merchandiseRepository.findAllByIsActiveOrderByPrice("1");
        return merchandiseRepository.findAllByIsActiveOrderById("1");
    }

    public List<Merchandise> findMerchandiseBySearch(@NotNull String search) {
        if(search.chars().allMatch(Character::isDigit)) return merchandiseRepository.findAllByPriceLessThanEqualAndIsActiveOrderByPriceDesc(search,"1");
        return contains(search, merchandiseRepository.findAllByIsActiveOrderById("1"));
    }

    public List<Merchandise> contains(String search, @NotNull List<Merchandise> list) {
        search = search.toLowerCase();
        List<Merchandise> newList = new ArrayList<>();
            for(Merchandise merchandise : list) {
                if(merchandise.getName().toLowerCase().startsWith(search)
                || merchandise.getId().toLowerCase().startsWith(search)) {
                    newList.add(merchandise);
                }
            }
        return newList;
    }

    public MerchandiseDiscount discount(String id, BigDecimal quantity) {
        return merchandiseDiscountRepository.discount(id,quantity);
    }

    public List<MerchandiseDiscount> findAllValidDiscount(String id) {
        return merchandiseDiscountRepository.findAllByIdAndIsValidOrderByDiscount(id,"1");
    }

    public List<MerchandiseDiscount> findAllInvalidDiscount(String id) {
        return merchandiseDiscountRepository.findAllByIdAndIsValidOrderByDiscount(id,"0");
    }

    public boolean hasStock(String id, Integer quantity) {
        final Optional<Merchandise> merch = merchandiseRepository.findById(id);
        if(merch.isEmpty()) return false;
        return Integer.parseInt(merch.get().getQuantityPerPieces()) >= quantity;
    }

    public void updateProductQuantity(Integer quantity, String id) {
        merchandiseRepository.updateProductQuantity(quantity,id);
    }

    public List<MerchandiseHistory> getAllProductHistory(String id) {
        return merchandiseHistoryRepository.findAllByIdOrderByCreatedAtDesc(id);
    }

    public Merchandise findProductById(String id) {
        Optional<Merchandise> merch = merchandiseRepository.findById(id);
        return merch.orElse(null);
    }

    public boolean isProductExist(String id) {
        return merchandiseRepository.existsById(id);
    }

    public void addProduct(Merchandise merchandise) {
        merchandiseRepository.save(merchandise);
    }

    @Transactional @Modifying
    public void updateProduct(@NotNull Merchandise merchandise) {
        Optional<Merchandise> merch = merchandiseRepository.findById(merchandise.getId());
        if(merch.isPresent() && !merch.get().equals(merchandise)) {
            saveProductHistory(merchandise);
            merchandiseRepository.updateProduct(
                    merchandise.getId(),
                    merchandise.getName(),
                    merchandise.getPrice(),
                    merchandise.getCapital()
            );
        }
    }

    public void saveProductHistory(@NotNull Merchandise merchandise) {
        merchandiseHistoryRepository.save(new MerchandiseHistory(
            "",
            merchandise.getId(),
            merchandise.getName(),
            merchandise.getPrice(),
            merchandise.getCapital(),
            ""
        ));
    }

    public void archiveProduct(String id) {
        merchandiseRepository.archiveProduct(id,"0");
    }

    public void unarchiveProduct(String id) {
        merchandiseRepository.archiveProduct(id,"1");
    }

    public void addProductDiscount(Integer quantity, Double discount, String id) {
        if(merchandiseDiscountRepository.existsByIdAndQuantity(id,quantity)) merchandiseDiscountRepository.updateDiscount(discount,id,quantity);
        else merchandiseDiscountRepository.save(new MerchandiseDiscount("",id,discount,quantity,"1"));
    }
}
