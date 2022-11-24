package com.capstone.backend.pojo;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class TransactionProductSummary {
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
