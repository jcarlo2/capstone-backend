package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_item_history", schema = "retail_management")
@Entity(name = "product_item_history")
public class DeliveryReportItemHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "report_id")
    private String reportId;
    @Column(name = "archived_at")
    private String timestamp;
}
