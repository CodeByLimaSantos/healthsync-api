package com.limasantos.pharmacy.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductMovementDTO {
    private Long movementId;
    private String type; // ENTRADA, SAIDA
    private Integer quantity;
    private LocalDateTime movementDate;
    private String description;
}

