package com.capstone.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SalesSummary {
    private Integer totalItem;
    private BigDecimal totalAmount;
    private BigDecimal totalCapital;
    private BigDecimal total;
}
