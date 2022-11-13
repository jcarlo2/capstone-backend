package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_report_item_history", schema = "retail_management")
@Entity(name = "transaction_report_item_history")
public class TransactionReportItemHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "sold")
    private Integer sold;
    @Column(name = "sold_total")
    private String soldTotal;
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "capital")
    private BigDecimal capital;
    @Column(name = "report_id")
    private String reportId;
    @Column(name = "archived_at")
    private String timestamp;
}
