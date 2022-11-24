package com.capstone.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoidSummary {
    private Integer totalItem;
    private Integer expiredItem;
    private Integer damagedItem;
    private BigDecimal totalLoss;
}

