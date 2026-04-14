package com.limasantos.pharmacy.api.inventory.repository;

import com.limasantos.pharmacy.api.inventory.entity.InventoryLot;
import com.limasantos.pharmacy.api.inventory.entity.InventoryMovements;
import com.limasantos.pharmacy.api.shared.enums.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovements, Long> {

    /**
     * Busca todas as movimentações de um lote específico.
     */
    List<InventoryMovements> findByInventoryLot(InventoryLot inventoryLot);

    /**
     * Busca movimentações por tipo (ENTRY, SALE_EXIT, DISPOSAL, etc).
     */
    List<InventoryMovements> findByMovementType(MovementType movementType);

    /**
     * Busca movimentações de um lote dentro de um período.
     */
    List<InventoryMovements> findByInventoryLotAndMovementDateBetween(
        InventoryLot inventoryLot,
        LocalDateTime startDate,
        LocalDateTime endDate
    );

    /**
     * Calcula o total de entradas por lote.
     */
    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM InventoryMovements m " +
           "WHERE m.inventoryLot = :lot AND m.movementType = :movementType")
    Integer sumQuantityByLotAndType(@Param("lot") InventoryLot lot,
                                     @Param("movementType") MovementType movementType);

    /**
     * Busca movimentações com motivo (para auditoria de descartes e ajustes).
     */
    List<InventoryMovements> findByReasonIsNotNull();
}
