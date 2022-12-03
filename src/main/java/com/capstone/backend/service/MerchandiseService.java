package com.capstone.backend.service;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.MerchandiseDiscount;
import com.capstone.backend.entity.MerchandiseDiscountHistory;
import com.capstone.backend.entity.MerchandiseHistory;
import com.capstone.backend.repository.MerchandiseDiscountHistoryRepository;
import com.capstone.backend.repository.MerchandiseDiscountRepository;
import com.capstone.backend.repository.MerchandiseHistoryRepository;
import com.capstone.backend.repository.MerchandiseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseHistoryRepository merchandiseHistoryRepository;
    private final MerchandiseDiscountRepository merchandiseDiscountRepository;
    private final MerchandiseDiscountHistoryRepository merchandiseDiscountHistoryRepository;

    public MerchandiseService(MerchandiseRepository merchandiseRepository, MerchandiseDiscountRepository merchandiseDiscountRepository, MerchandiseHistoryRepository merchandiseHistoryRepository, MerchandiseDiscountHistoryRepository merchandiseDiscountHistoryRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.merchandiseDiscountRepository = merchandiseDiscountRepository;
        this.merchandiseHistoryRepository = merchandiseHistoryRepository;
        this.merchandiseDiscountHistoryRepository = merchandiseDiscountHistoryRepository;
    }

    public List<Merchandise> getAllMerchandise(@NotNull String filter) {
        return switch (filter) {
            case "Filter By Name" -> merchandiseRepository.findAllByIsActiveOrderByName("1");
            case "Filter By Stock H-L" -> merchandiseRepository.findAllByIsActiveOrderByQuantityPerPiecesDesc("1");
            case "Filter By Stock L-H" -> merchandiseRepository.findAllByIsActiveOrderByQuantityPerPieces("1");
            case "Filter By Price H-L" -> merchandiseRepository.findAllByIsActiveOrderByPriceDesc("1");
            case "Filter By Price L-H" -> merchandiseRepository.findAllByIsActiveOrderByPrice("1");
            default -> merchandiseRepository.findAllByIsActiveOrderById("1");
        };
    }

    public List<Merchandise> findMerchandiseBySearch(@NotNull String search, String filter) {
        List<Merchandise> merch;
        if(search.chars().allMatch(Character::isDigit)) merch = merchandiseRepository.findAllByPriceLessThanEqualAndIsActiveOrderByPriceDesc(new BigDecimal(search),"1");
        else merch = contains(search, merchandiseRepository.findAllByIsActiveOrderById("1"));

        switch (filter) {
            case "Filter By Name" -> merch.sort(Comparator.comparing(Merchandise::getName));
            case "Filter By Stock H-L" -> merch.sort(Comparator.comparing(Merchandise::getQuantityPerPieces).reversed());
            case "Filter By Stock L-H" -> merch.sort(Comparator.comparing(Merchandise::getQuantityPerPieces));
            case "Filter By Price H-L" -> merch.sort(Comparator.comparing(Merchandise::getPrice).reversed());
            case "Filter By Price L-H" -> merch.sort(Comparator.comparing(Merchandise::getPrice));
            default -> merch.sort(Comparator.comparing(Merchandise::getId));
        }
        return merch;
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

    public List<MerchandiseDiscountHistory> findAllArchivedDiscountById(String id) {
        return merchandiseDiscountHistoryRepository.findAllByIdOrderByTimestampDesc(id);
    }

    public boolean hasStock(String id, Integer quantity) {
        final Optional<Merchandise> merch = merchandiseRepository.findById(id);
        if(merch.isEmpty()) return false;
        return merch.get().getQuantityPerPieces() >= quantity;
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
        Merchandise merch = merchandiseRepository.findById(merchandise.getId()).orElse(null);
        if(merch == null) return;
        merchandiseHistoryRepository.save(new MerchandiseHistory(
            "",
            merchandise.getId().equals(merch.getId()) ? merchandise.getId() : merch.getId(),
            merchandise.getName().equals(merch.getName()) ? merchandise.getName() : merch.getName(),
            merchandise.getPrice().compareTo(merch.getPrice()) == 0 ? merchandise.getPrice() : merch.getPrice(),
            merchandise.getCapital().compareTo(merch.getCapital()) == 0 ? merchandise.getCapital() : merch.getCapital(),
            ""
        ));
    }

    public void archiveProduct(String id) {
        merchandiseRepository.setProductIsActive(id,"0");
    }

    public void unarchiveProduct(String id) {
        merchandiseRepository.setProductIsActive(id,"1");
    }

    public void addProductDiscount(Integer quantity, Double discount, String id) {
        if(merchandiseDiscountRepository.existsByIdAndQuantity(id,quantity)) merchandiseDiscountRepository.updateDiscount(discount,id,quantity);
        else merchandiseDiscountRepository.save(new MerchandiseDiscount("",id,discount,quantity,"1"));
    }

    public boolean checkIfDiscountQuantityExist(String id, Integer quantity) {
        return merchandiseDiscountRepository.existsByIdAndQuantity(id,quantity);
    }

    public void archiveProductDiscount(String id, Integer quantity) {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        MerchandiseDiscount discount = merchandiseDiscountRepository.findByIdAndQuantity(id,quantity);
        merchandiseDiscountHistoryRepository.save(new MerchandiseDiscountHistory(
                "",
                discount.getId(),
                discount.getDiscount(),
                discount.getQuantity(),
                timestamp
        ));
        merchandiseDiscountRepository.archiveProductDiscount(id,quantity);
    }

    public List<Merchandise> findAllInactiveProduct() {
        return merchandiseRepository.findAllByIsActiveOrderById("0");
    }

    public void activateProduct(String id, boolean isZero) {
        if(isZero) merchandiseRepository.setProductActiveWithZeroQuantity(id);
        else merchandiseRepository.setProductIsActive(id,"1");
    }

    public boolean updateProductDiscount(String id, Integer quantity, Double discount, Integer quantityUpdate, Double discountUpdate) {
        MerchandiseDiscount discountEntity = merchandiseDiscountRepository.save(new MerchandiseDiscount("",id,discountUpdate,quantityUpdate,"1"));
        if(merchandiseDiscountRepository.countAllByIdAndQuantityAndDiscount(id,quantityUpdate,discountUpdate) > 1) {
            merchandiseDiscountRepository.deleteAllByIdAndQuantityAndDiscount(id,quantityUpdate,discountUpdate);
            merchandiseDiscountRepository.save(discountEntity);
            return false;
        }
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        merchandiseDiscountRepository.deleteAllByIdAndQuantityAndDiscount(id,quantity,discount);
        merchandiseDiscountHistoryRepository.save(new MerchandiseDiscountHistory("",id,discount,quantity,timestamp));
        return true;
    }

    public boolean isMerchandiseDiscountExist(String id, Integer quantity) {
        return merchandiseDiscountRepository.existsByIdAndQuantity(id,quantity);
    }

    public void activateDiscount(String id, Integer quantity, Double discount, boolean isOverride) {
        if(isOverride) merchandiseDiscountRepository.deleteAllByIdAndQuantity(id,quantity);
        merchandiseDiscountRepository.save(new MerchandiseDiscount("",id,discount,quantity,"1"));
    }
}
