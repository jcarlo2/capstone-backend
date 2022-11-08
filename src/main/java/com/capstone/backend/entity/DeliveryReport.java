package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_report", schema = "retail_management")
@Entity(name = "product_report")
public class DeliveryReport {
    @Id @Column(name = "id")
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
    @Column(name = "reason")
    private String reason;
    @Column(name = "link")
    private String link;
}
