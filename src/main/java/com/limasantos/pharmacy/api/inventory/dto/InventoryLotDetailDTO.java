package com.limasantos.pharmacy.api.inventory.dto;

import com.limasantos.pharmacy.api.shared.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryLotDetailDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String lotNumber;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private Integer initialQuantity; // Quantidade na entrada
    private Integer currentQuantity; // Quantidade disponível agora
    private Integer totalMoved; // Total movimentado (saídas + descartes)
    private List<InventoryMovementDTO> movements; // Histórico de movimentações
    private Boolean isExpired; // Se o lote está vencido
    private Integer daysUntilExpiration; // Dias para vencer
}

