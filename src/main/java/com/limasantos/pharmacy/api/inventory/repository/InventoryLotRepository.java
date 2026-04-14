package com.limasantos.pharmacy.api.inventory.repository;

import com.limasantos.pharmacy.api.inventory.entity.InventoryLot;
import com.limasantos.pharmacy.api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryLotRepository extends JpaRepository<InventoryLot, Long> {

    /**
     * Busca todos os lotes de um produto específico.
     */
    List<InventoryLot> findByProduct(Product product);

    /**
     * Busca lotes do produto com quantidade disponível maior que zero.
     */
    List<InventoryLot> findByProductAndQuantityGreaterThan(Product product, Integer quantity);

    /**
     * Busca lotes vencidos até uma data específica.
     */
    List<InventoryLot> findByExpirationDateBefore(LocalDate cutoffDate);

    /**
     * Busca lotes que vencem em um período (para alerta de vencimento próximo).
     */
    List<InventoryLot> findByExpirationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Busca lote por número do lote.
     */
    List<InventoryLot> findByLotNumber(String lotNumber);
}
