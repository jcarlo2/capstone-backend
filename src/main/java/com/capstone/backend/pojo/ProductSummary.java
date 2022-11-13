package com.capstone.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductSummary {
    private String id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Double discount;
    private BigDecimal capital;
    private BigDecimal totalPrice;
    private BigDecimal totalCapital;
    private BigDecimal profit;
}
