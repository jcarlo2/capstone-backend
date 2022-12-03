package com.capstone.backend.controller;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.MerchandiseDiscount;
import com.capstone.backend.entity.MerchandiseDiscountHistory;
import com.capstone.backend.entity.MerchandiseHistory;
import com.capstone.backend.facade.MerchandiseFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class MerchandiseController {
    private final MerchandiseFacade facade;

    public MerchandiseController(MerchandiseFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/all-merchandise")
    public List<Merchandise> findAllMerchandise(@RequestParam String filter) {
        return facade.getAllMerchandise(filter);
    }

    @GetMapping("/search-merchandise")
    public List<Merchandise> findMerchandiseBySearch(@RequestParam String search, @RequestParam String filter) {
        return facade.findMerchandiseBySearch(search,filter);
    }

    @GetMapping( "/quantity-discount")
    public MerchandiseDiscount getDiscount(@RequestParam String id, @RequestParam BigDecimal quantity) {
        return facade.discount(id,quantity);
    }

    @GetMapping("/verify-stock")
    public ResponseEntity<Boolean> hasStock(@RequestParam String id, @RequestParam Integer quantity) {
        return new ResponseEntity<>(facade.hasStock(id,quantity), HttpStatus.OK);
    }

    @PostMapping("/update-quantity")
    public void updateProductQuantity(@RequestParam Integer quantity, @RequestParam String id) {
        facade.updateProductQuantity(quantity,id);
    }

    @GetMapping("/find-all-valid-discount")
    public List<MerchandiseDiscount> findAllValidDiscount(@RequestParam String id) {
        return facade.findAllValidDiscount(id);
    }

    @GetMapping("/find-all-archived-discount-id")
    public List<MerchandiseDiscountHistory> findAllArchivedDiscountById(@RequestParam String id) {
        return facade.findAllArchivedDiscountById(id);
    }

    @GetMapping("/get-product-history")
    public List<MerchandiseHistory> getAllProductHistory(@RequestParam String id) {
        return facade.getAllProductHistory(id);
    }

    @GetMapping("/find-product-by-id")
    public Merchandise findProductById(@RequestParam String id) {
        return facade.findProductById(id);
    }

    @GetMapping("/is-product-exist")
    public boolean isProductExist(@RequestParam String id) {
        return facade.isProductExist(id);
    }

    @PostMapping("/add-product")
    public void addProduct(@RequestBody Merchandise merchandise) {
        facade.addProduct(merchandise);
    }

    @PostMapping("/update-product")
    public void updateProduct(@RequestBody Merchandise merchandise) {
        facade.updateProduct(merchandise);
    }

    @GetMapping("/product-archive")
    public void archiveProduct(@RequestParam String id) {
        facade.archiveProduct(id);
    }

    @GetMapping("/product-unarchive")
    public void unarchiveProduct(@RequestParam String id) {
        facade.unarchiveProduct(id);
    }

    @PostMapping("/add-product-discount")
    public void addProductDiscount(@RequestParam Integer quantity, @RequestParam Double discount, @RequestParam String id) {
        facade.addProductDiscount(quantity, discount,id);
    }

    @GetMapping("/check-discount-quantity-exist")
    public boolean checkIfDiscountQuantityExist(@RequestParam String id, @RequestParam Integer quantity) {
        return facade.checkIfDiscountQuantityExist(id,quantity);
    }

    @GetMapping("/is-exist-discount")
    public boolean isMerchandiseDiscountExist(@RequestParam String id, @RequestParam Integer quantity) {
        return facade.isMerchandiseDiscountExist(id,quantity);
    }

    @PostMapping("/discount-activate")
    public void activateDiscount(@RequestParam String id, @RequestParam Integer quantity, @RequestParam Double discount, @RequestParam boolean isOverride) {
        facade.activateDiscount(id,quantity,discount,isOverride);
    }

    @PostMapping("/archive-product-discount")
    public void archiveProductDiscount(@RequestParam String id, @RequestParam Integer quantity) {
        facade.archiveProductDiscount(id,quantity);
    }

    @PostMapping("/update-product-discount")
    public boolean updateProductDiscount(@RequestParam String id, @RequestParam Integer quantity, @RequestParam Double discount, @RequestParam Integer quantityUpdate, @RequestParam Double discountUpdate) {
        return facade.updateProductDiscount(id,quantity,discount,quantityUpdate,discountUpdate);
    }

    @GetMapping("/product-archive-list")
    public List<Merchandise> findAllInactiveProduct() {
            return facade.findAllInactiveProduct();
    }

    @PostMapping("activate-product")
    public void activateProduct(@RequestParam String id, @RequestParam boolean isZero) {
        facade.activateProduct(id,isZero);
    }
}
