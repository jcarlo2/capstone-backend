package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_discount_history", schema = "retail_management")
@Entity(name = "product_discount_history")
public class MerchandiseDiscountHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "id")
    private String id;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "archived_at")
    private String timestamp;
}
