package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "retail_management")
@Entity(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private int role;
    @Column(name = "timestamp",insertable = false)
    private String timestamp;

    @Transient
    private boolean isSave;
}
