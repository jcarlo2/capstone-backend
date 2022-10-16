package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "null_item", schema = "retail_management")
@Entity(name = "null_item")
public class NullReportItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "discount")
    private String discount;
    @Column(name = "total_amount")
    private String totalAmount;
    @Column(name = "capital")
    private String capital;
    @Column(name = "report_id")
    private String reportId;
    @Transient
    private String reason;
    @Transient
    private String link;
}
