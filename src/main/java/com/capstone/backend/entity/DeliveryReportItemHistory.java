package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String discountPercentage;
    @Column(name = "total_price")
    private String totalPrice;
    @Column(name = "total_amount")
    private String totalAmount;
    @Column(name = "report_id")
    private String reportId;
    @Column(name = "archived_at")
    private String timestamp;
}
