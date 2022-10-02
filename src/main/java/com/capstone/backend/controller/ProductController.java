package com.capstone.backend.controller;

import com.capstone.backend.entity.Merchandise;
import com.capstone.backend.entity.ProductDiscount;
import com.capstone.backend.facade.ProductFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductFacade facade;

    public ProductController(ProductFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/all-merchandise")
    public ResponseEntity<List<Merchandise>> findAllMerchandise() {
        return new ResponseEntity<>(facade.getAllMerchandise(), HttpStatus.OK);
    }

    @GetMapping("/search-merchandise")
    public ResponseEntity<List<Merchandise>> findMerchandiseBySearch(@RequestParam String search) {
        return new ResponseEntity<>(facade.findMerchandiseBySearch(search), HttpStatus.OK);
    }

    @GetMapping( "/quantity-discount")
    public ResponseEntity<ProductDiscount> getDiscount(@RequestParam(name = "id") String id,
                                                       @RequestParam(name = "quantity") BigDecimal quantity) {
        return new ResponseEntity<>(facade.discount(id,quantity), HttpStatus.OK);
    }

    @GetMapping("/verify-stock")
    public ResponseEntity<Boolean> hasStock(@RequestParam(name = "id") String id,
                                            @RequestParam(name = "quantity") Integer quantity) {
        return new ResponseEntity<>(facade.hasStock(id,quantity), HttpStatus.OK);
    }

    @PostMapping("/update-quantity")
    public void updateProductQuantity(@RequestParam Integer quantity,
                                      @RequestParam String id) {
        facade.updateProductQuantity(quantity,id);
    }
}
