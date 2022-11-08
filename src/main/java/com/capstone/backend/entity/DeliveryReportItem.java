package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_item", schema = "retail_management")
@Entity(name = "product_item")
public class DeliveryReportItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    @Column(name = "prod_id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity_pieces")
    private Integer quantity;
    @Column(name = "total_price")
    private String totalPrice;
    @Column(name = "discount_percent")
    private String discountPercentage;
    @Column(name = "total_amount")
    private String totalAmount;
    @Column(name = "unique_id")
    private String uniqueId;
    @Column(name = "capital")
    private String capital;
}
