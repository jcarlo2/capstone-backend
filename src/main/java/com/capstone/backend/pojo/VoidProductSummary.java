package com.capstone.backend.pojo;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class VoidProductSummary {
    private String id;
    private String name;
    private String reason;
    private Integer quantity;
    private BigDecimal capital;
    private BigDecimal totalCapital;
}