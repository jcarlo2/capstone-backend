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
@Table(name = "null_item", schema = "retail_management")
@Entity(name = "null_item")
public class NullReportItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "capital")
    private BigDecimal capital;
    @Column(name = "report_id")
    private String reportId;
    @Column(name = "reason")
    private String reason;
    @Transient
    private String link;
}
