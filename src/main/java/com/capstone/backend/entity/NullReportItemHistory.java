package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "null_item_history", schema = "retail_management")
@Entity(name = "null_item_history")
public class NullReportItemHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    @Column(name = "product_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "total")
    private BigDecimal totalAmount;
    @Column(name = "capital")
    private BigDecimal capital;
    @Column(name = "report_id")
    private String reportId;
    @Column(name = "archived_at")
    private String timestamp;
}
