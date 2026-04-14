package com.limasantos.pharmacy.api.inventory.entity;

import com.limasantos.pharmacy.api.shared.enums.MovementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_inventory_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovements {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_lot_id", nullable = false)
    @NotNull
    private InventoryLot inventoryLot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private MovementType movementType;

    @Column(nullable = false, updatable = false)
    @Positive
    private Integer quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime movementDate;

    private String reason;

    @PrePersist
    protected void onCreate() {
        movementDate = LocalDateTime.now();
        validateReasonForMovementType();
    }

    /**
     * Valida se o motivo é obrigatório para tipos de movimentação que exigem rastreabilidade.
     * DISPOSAL e ADJUSTMENT_OUT devem sempre ter motivo registrado.
     */
    private void validateReasonForMovementType() {
        if (movementType != null && movementType.requiresReason()) {
            if (reason == null || reason.isBlank()) {
                throw new IllegalArgumentException(
                    "Motivo é obrigatório para movimentação do tipo: " + movementType
                );
            }
        }
    }
}
