package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_report", schema = "retail_management")
@Entity(name = "product_report")
public class DeliveryDetail {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user")
    private String user;
    @Column(name = "total")
    private String total;
    @Column(name = "date", insertable = false, updatable = false)
    private String date;
    @Column(name = "date_time", insertable = false,updatable = false)
    private String timestamp;
    @Column(name = "is_valid")
    private String isValid;
}
