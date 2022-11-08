package com.capstone.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "null_report_history", schema = "retail_management")
@Entity(name = "null_report_history")
public class NullReportHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "id")
    private String id;
    @Column(name = "user")
    private String user;
    @Column(name = "total")
    private String total;
    @Column(name = "reason")
    private String reason;
    @Column(name = "archived_at")
    private String timestamp;
    @Column(name = "link")
    private String link;
}
