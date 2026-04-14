package com.limasantos.pharmacy.api.inventory.dto;

import com.limasantos.pharmacy.api.shared.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryMovementDTO {
    private Long id;
    private Long inventoryLotId;
    private String lotNumber;
    private Long productId;
    private String productName;
    private MovementType movementType;
    private Integer quantity;
    private LocalDateTime movementDate;
    private String reason;
}

