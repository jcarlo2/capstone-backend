package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_discount", schema = "retail_management")
@Entity(name = "product_discount")
public class ProductDiscount {

    @Id
    @Column(name = "no")
    private Integer num;
    @Column(name = "id")
    private String id;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "quantity")
    private Integer quantity;

}
