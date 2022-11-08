package com.capstone.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "null_report", schema = "retail_management")
@Entity(name = "null_report")
public class NullReport {
    @Id @Column(name = "id")
    private String id;
    @Column(name = "user")
    private String user;
    @Column(name = "total_amount")
    private String total;
    @Column(name = "date",insertable = false,updatable = false)
    private String date;
    @Column(name = "date_time",insertable = false,updatable = false)
    private String timestamp;
    @Column(name = "link")
    private String link;
    @Column(name = "is_valid")
    private String isValid;
    @Column(name = "reason")
    private String reason;
}
