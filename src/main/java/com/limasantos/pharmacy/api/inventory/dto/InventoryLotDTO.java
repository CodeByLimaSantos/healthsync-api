package com.limasantos.pharmacy.api.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryLotDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String lotNumber;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private Integer quantity;
    private Integer availableQuantity;
}

//listagem de lotes de inventário, incluindo informações do produto, número do lote,
// datas de entrada e expiração, quantidade total e quantidade disponível.