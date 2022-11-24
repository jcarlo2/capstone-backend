package com.capstone.backend.pojo;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryProductSummary {
    private String id;
    private String name;
    private Integer quantity;
    private Double discount;
    private BigDecimal totalCost;
}