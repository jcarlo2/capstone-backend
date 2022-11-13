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
    public ResponseEntity<List<Merchandise>> findAllMerchandise(@RequestParam String filter) {
        return new ResponseEntity<>(facade.getAllMerchandise(filter), HttpStatus.OK);
    }

    @GetMapping("/search-merchandise")
    public ResponseEntity<List<Merchandise>> findMerchandiseBySearch(@RequestParam String search) {
        return new ResponseEntity<>(facade.findMerchandiseBySearch(search), HttpStatus.OK);
    }

    @GetMapping( "/quantity-discount")
    public ResponseEntity<MerchandiseDiscount> getDiscount(@RequestParam String id, @RequestParam BigDecimal quantity) {
        return new ResponseEntity<>(facade.discount(id,quantity), HttpStatus.OK);
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

    @PostMapping("/archive-product-discount")
    public void archiveProductDiscount(@RequestParam String id, @RequestParam Integer quantity) {
        facade.archiveProductDiscount(id,quantity);
    }

    @GetMapping("/product-archive-list")
    public List<Merchandise> findAllInactiveProduct() {
            return facade.findAllInactiveProduct();
    }

    @PostMapping("unarchived-product")
    public void unarchivedProduct(@RequestParam String id, @RequestParam boolean isZero) {
        facade.unarchivedProduct(id,isZero);
    }
}
