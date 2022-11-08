package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_report_history", schema = "retail_management")
@Entity(name = "product_report_history")
public class DeliveryReportHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "id")
    private String id;
    @Column(name = "user")
    private String user;
    @Column(name = "total_amount")
    private String total;
    @Column(name = "reason")
    private String reason;
    @Column(name = "archived_at")
    private String timestamp;
    @Column(name = "link")
    private String link;
}
