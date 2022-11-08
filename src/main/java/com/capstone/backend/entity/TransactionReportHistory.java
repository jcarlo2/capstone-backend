package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_report_history", schema = "retail_management")
@Entity(name = "transaction_report_history")
@ToString
public class TransactionReportHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "id")
    private String id;
    @Column(name = "user")
    private String user;
    @Column(name = "total")
    private String totalAmount;
    @Column(name = "archived_at")
    private String timestamp;
}
