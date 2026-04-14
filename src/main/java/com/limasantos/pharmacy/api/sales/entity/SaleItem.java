package com.limasantos.pharmacy.api.sales.entity;

import com.limasantos.pharmacy.api.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "TB_sale_items")
@Getter
@Setter
public class SaleItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sale_id", nullable = false)
        @NotNull
        private Sale sale;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id", nullable = false)
        @NotNull
        private Product product;

        @Positive
        @Column(nullable = false)
        private Integer quantity;

        @PositiveOrZero
        @Column(nullable = false)
        private BigDecimal priceAtSale;


}
