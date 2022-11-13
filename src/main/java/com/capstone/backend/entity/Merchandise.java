package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product", schema = "retail_management")
@Entity(name = "product")
public class Merchandise {
    @Id @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity_per_pieces")
    private Integer quantityPerPieces;
    @Column(name = "pieces_per_box")
    private Integer piecesPerBox;
    @Column(name = "quantity_per_box",insertable = false)
    private Double quantityPerBox;
    @Column(name = "capital")
    private BigDecimal capital;
    @Column(name = "is_active")
    private String isActive;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Merchandise c))  return false;
        return c.getName().equals(name)
                && c.getPrice().equals(price)
                && c.getCapital().equals(capital);
    }
}
