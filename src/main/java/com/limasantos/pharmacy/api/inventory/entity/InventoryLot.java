package com.limasantos.pharmacy.api.inventory.entity;

import com.limasantos.pharmacy.api.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_inventory_lot")

public class InventoryLot {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String lotNumber;

    @Column(nullable = false, updatable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private Integer quantity;

    @PrePersist
    protected void onCreate() {
        this.entryDate = java.time.LocalDate.now();

    }
}
