package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@AllArgsConstructor
@Table(name = "product", schema = "retail_management")
@Entity(name = "product")
public class Merchandise {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "quantity_per_pieces")
    private String quantityPerPieces;
    @Column(name = "pieces_per_box")
    private String piecesPerBox;
    @Column(name = "quantity_per_box",insertable = false)
    private String quantityPerBox;

    public Merchandise() {
    }
}
