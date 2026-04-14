package com.limasantos.pharmacy.api.inventory.dto;

import com.limasantos.pharmacy.api.shared.enums.MovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateInventoryMovementDTO {

    @NotNull(message = "O ID do lote é obrigatório")
    private Long inventoryLotId;

    @NotNull(message = "O tipo de movimentação é obrigatório")
    private MovementType movementType;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser positiva")
    private Integer quantity;

    // Campo opcional, mas obrigatório para certos tipos de movimento
    private String reason;
}

