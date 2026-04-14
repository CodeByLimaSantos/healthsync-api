package com.limasantos.pharmacy.api.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductStockSummaryDTO {
    private Long productId;
    private String productName;
    private String productCode; // SKU ou código do produto
    private Integer totalStock; // Saldo total do produto
    private Integer lotsCount; // Quantidade de lotes
    private Integer expiredCount; // Lotes vencidos
    private Integer expiringCount; // Lotes vencendo em 30 dias
    private BigDecimal minPrice; // Preço mínimo nos lotes
    private BigDecimal maxPrice; // Preço máximo nos lotes
}

