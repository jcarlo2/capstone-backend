package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@Table(name = "transaction_report_item", schema = "retail_management")
@Entity(name = "transaction_report_item")
public class TransactionItemDetail {

    @Id
    @Column(name = "num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column(name = "prod_id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "sold")
    private Integer sold;
    @Column(name = "sold_total")
    private String soldTotal;
    @Column(name = "discount_percentage")
    private String discountPercentage;
    @Column(name = "total_amount")
    private String totalAmount;
    @Column(name = "unique_id")
    private String uniqueId;

    public TransactionItemDetail() {
    }
}
