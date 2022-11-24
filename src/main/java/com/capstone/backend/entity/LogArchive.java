package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log_archive", schema = "retail_management")
@Entity(name = "log_archive")
public class LogArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String no;
    @Column(name = "user")
    private String user;
    @Column(name = "action")
    private String action;
    @Column(name = "description")
    private String description;
    @Column(name = "date_time")
    private String timestamp;
}
