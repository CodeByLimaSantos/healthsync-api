package com.limasantos.pharmacy.api.inventory.service;

import com.limasantos.pharmacy.api.inventory.entity.InventoryLot;
import com.limasantos.pharmacy.api.inventory.entity.InventoryMovements;
import com.limasantos.pharmacy.api.inventory.repository.InventoryLotRepository;
import com.limasantos.pharmacy.api.inventory.repository.InventoryMovementRepository;
import com.limasantos.pharmacy.api.shared.enums.MovementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryLotRepository lotRepository;
    private final InventoryMovementRepository movementRepository;
    private final com.limasantos.pharmacy.api.product.repository.ProductRepository productRepository;

    // ==========================================
    // REGISTRO DE MOVIMENTAÇÕES
    // ==========================================


    @Transactional
    public InventoryMovements registrarEntrada(InventoryLot lot, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade de entrada deve ser positiva");
        }

        // Salva o lote se for novo
        if (lot.getId() == null) {
            lotRepository.save(lot);
        }

        InventoryMovements movement = new InventoryMovements();
        movement.setInventoryLot(lot);
        movement.setMovementType(MovementType.ENTRY);
        movement.setQuantity(quantidade);

        return movementRepository.save(movement);
    }

    /**
     * Registra saída por venda.
     * Usa estratégia FEFO (First Expire, First Out) para selecionar o lote.
     */
    @Transactional
    public InventoryMovements registrarSaidaPorVenda(InventoryLot lot, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade de saída deve ser positiva");
        }

        int disponivel = calcularQuantidadeDisponivel(lot);
        if (quantidade > disponivel) {
            throw new IllegalArgumentException(
                String.format("Quantidade insuficiente no lote %s. Disponível: %d, Solicitado: %d",
                    lot.getLotNumber(), disponivel, quantidade)
            );
        }

        InventoryMovements movement = new InventoryMovements();
        movement.setInventoryLot(lot);
        movement.setMovementType(MovementType.SALE_EXIT);
        movement.setQuantity(quantidade);

        return movementRepository.save(movement);
    }

    /**
     * Registra ajuste positivo de inventário.
     */
    @Transactional
    public InventoryMovements registrarAjusteEntrada(InventoryLot lot, Integer quantidade, String motivo) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do ajuste deve ser positiva");
        }

        InventoryMovements movement = new InventoryMovements();
        movement.setInventoryLot(lot);
        movement.setMovementType(MovementType.ADJUSTMENT_IN);
        movement.setQuantity(quantidade);
        movement.setReason(motivo);

        return movementRepository.save(movement);
    }

    /**
     * Registra ajuste negativo de inventário.
     * Motivo é obrigatório.
     */
    @Transactional
    public InventoryMovements registrarAjusteSaida(InventoryLot lot, Integer quantidade, String motivo) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do ajuste deve ser positiva");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo é obrigatório para ajuste de saída");
        }

        int disponivel = calcularQuantidadeDisponivel(lot);
        if (quantidade > disponivel) {
            throw new IllegalArgumentException(
                String.format("Quantidade insuficiente no lote %s para ajuste. Disponível: %d",
                    lot.getLotNumber(), disponivel)
            );
        }

        InventoryMovements movement = new InventoryMovements();
        movement.setInventoryLot(lot);
        movement.setMovementType(MovementType.ADJUSTMENT_OUT);
        movement.setQuantity(quantidade);
        movement.setReason(motivo);

        return movementRepository.save(movement);
    }

    /**
     * Registra descarte por vencimento ou avaria.
     * Motivo é obrigatório.
     */
    @Transactional
    public InventoryMovements registrarDescarte(InventoryLot lot, Integer quantidade, String motivo) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade de descarte deve ser positiva");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo é obrigatório para descarte");
        }

        int disponivel = calcularQuantidadeDisponivel(lot);
        if (quantidade > disponivel) {
            throw new IllegalArgumentException(
                String.format("Quantidade insuficiente no lote %s para descarte. Disponível: %d",
                    lot.getLotNumber(), disponivel)
            );
        }

        InventoryMovements movement = new InventoryMovements();
        movement.setInventoryLot(lot);
        movement.setMovementType(MovementType.DISPOSAL);
        movement.setQuantity(quantity);
        movement.setReason(reason);

        return movementRepository.save(movement);
    }

    // ==========================================
    // CONSULTAS DE SALDO
    // ==========================================

    /**
     * Calcula a quantidade disponível de um lote.
     * Fórmula: ENTRY + ADJUSTMENT_IN - SALE_EXIT - ADJUSTMENT_OUT - DISPOSAL
     */
    @Transactional(readOnly = true)
    public int calcularQuantidadeDisponivel(InventoryLot lot) {
        List<InventoryMovements> movimentacoes = movementRepository.findByInventoryLot(lot);

        int entradas = movimentacoes.stream()
            .filter(m -> m.getMovementType() == MovementType.ENTRY ||
                         m.getMovementType() == MovementType.ADJUSTMENT_IN)
            .mapToInt(InventoryMovements::getQuantity)
            .sum();

        int saidas = movimentacoes.stream()
            .filter(m -> m.getMovementType() == MovementType.SALE_EXIT ||
                         m.getMovementType() == MovementType.ADJUSTMENT_OUT ||
                         m.getMovementType() == MovementType.DISPOSAL)
            .mapToInt(InventoryMovements::getQuantity)
            .sum();

        return entradas - saidas;
    }

    /**
     * Calcula saldo consolidado de um produto (soma de todos os lotes).
     */
    @Transactional(readOnly = true)
    public int calcularSaldoProduto(Long productId) {
        List<InventoryLot> lotes = lotRepository.findAll();
        return lotes.stream()
            .filter(lot -> lot.getProduct().getId().equals(productId))
            .mapToInt(this::calcularQuantidadeDisponivel)
            .sum();
    }

    // ==========================================
    // GESTÃO DE LOTES
    // ==========================================

    /**
     * Busca o melhor lote para venda usando estratégia FEFO
     * (First Expire, First Out - primeiro que vence, primeiro que sai).
     */
    @Transactional(readOnly = true)
    public Optional<InventoryLot> buscarMelhorLoteParaVenda(Long productId, Integer quantidadeNecessaria) {
        // Buscar todos os lotes do produto
        List<InventoryLot> lotes = lotRepository.findAll().stream()
            .filter(lot -> lot.getProduct().getId().equals(productId))
            .toList();

        return lotes.stream()
            .filter(lot -> calcularQuantidadeDisponivel(lot) >= quantidadeNecessaria)
            .filter(lot -> lot.getExpirationDate().isAfter(LocalDate.now()))
            .min(Comparator.comparing(InventoryLot::getExpirationDate));
    }

    /**
     * Retorna lotes vencidos para descarte.
     */
    @Transactional(readOnly = true)
    public List<InventoryLot> buscarLotesVencidos() {
        return lotRepository.findByExpirationDateBefore(LocalDate.now());
    }

    /**
     * Retorna lotes que vencem em um período (alerta de vencimento próximo).
     */
    @Transactional(readOnly = true)
    public List<InventoryLot> buscarLotesVencendoEm(int dias) {
        LocalDate hoje = LocalDate.now();
        LocalDate futuro = hoje.plusDays(dias);
        return lotRepository.findByExpirationDateBetween(hoje, futuro);
    }

    /**
     * Processa descarte automático de lotes vencidos.
     */
    @Transactional
    public List<InventoryMovements> processarVencidos() {
        List<InventoryLot> vencidos = buscarLotesVencidos();
        return vencidos.stream()
            .map(lot -> {
                int disponivel = calcularQuantidadeDisponivel(lot);
                if (disponivel > 0) {
                    return registrarDescarte(lot, disponivel,
                        "Descarte automático por vencimento: " + lot.getExpirationDate());
                }
                return null;
            })
            .filter(m -> m != null)
            .toList();
    }

    // ==========================================
    // HISTÓRICO E AUDITORIA
    // ==========================================

    /**
     * Retorna histórico completo de movimentações de um lote.
     */
    @Transactional(readOnly = true)
    public List<InventoryMovements> buscarHistoricoLote(Long lotId) {
        InventoryLot lot = lotRepository.findById(lotId)
            .orElseThrow(() -> new IllegalArgumentException("Lote não encontrado: " + lotId));
        return movementRepository.findByInventoryLot(lot);
    }

    /**
     * Retorna todas as movimentações com motivo (para auditoria).
     */
    @Transactional(readOnly = true)
    public List<InventoryMovements> findMovementsWithReason() {
        return movementRepository.findByReasonIsNotNull();
    }
}
