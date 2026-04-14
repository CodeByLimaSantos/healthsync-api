package com.limasantos.pharmacy.api.inventory.mapper;

import com.limasantos.pharmacy.api.inventory.dto.CreateInventoryLotDTO;
import com.limasantos.pharmacy.api.inventory.dto.InventoryLotDTO;
import com.limasantos.pharmacy.api.inventory.dto.InventoryLotDetailDTO;
import com.limasantos.pharmacy.api.inventory.dto.InventoryMovementDTO;
import com.limasantos.pharmacy.api.inventory.entity.InventoryLot;
import com.limasantos.pharmacy.api.product.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class InventoryLotMapper {

    // Método para converter uma entidade InventoryLot em um DTO simples
    public InventoryLotDTO toDTO(InventoryLot lot) {
        if (lot == null) {
            return null;
        }
        
        InventoryLotDTO dto = new InventoryLotDTO();
        dto.setId(lot.getId());
        dto.setLotNumber(lot.getLotNumber());
        dto.setEntryDate(lot.getEntryDate());
        dto.setExpirationDate(lot.getExpirationDate());
        dto.setQuantity(lot.getQuantity());
        
        if (lot.getProduct() != null) {
            dto.setProductId(lot.getProduct().getId());
            dto.setProductName(lot.getProduct().getName());
        }
        
        return dto;
    }

    // Método para converter uma lista de entidades InventoryLot em uma lista de DTOs
    public List<InventoryLotDTO> toDTOList(List<InventoryLot> lots) {
        return lots.stream()
                .map(this::toDTO)
                .toList();
    }

    // Método para criar uma entidade InventoryLot a partir de um DTO de criação
    public InventoryLot toEntity(CreateInventoryLotDTO dto, Product product) {
        if (dto == null) {
            return null;
        }
        
        InventoryLot lot = new InventoryLot();
        lot.setProduct(product);
        lot.setLotNumber(dto.getLotNumber());
        lot.setExpirationDate(dto.getExpirationDate());
        lot.setQuantity(dto.getQuantity());
        
        return lot;
    }

    // Método para converter uma entidade InventoryLot em um DTO detalhado, incluindo movimentos e status de expiração
    public InventoryLotDetailDTO convertToDetailDTO(InventoryLot lot, Integer currentQuantity, 
                                             Integer totalMoved, List<InventoryMovementDTO> movements) {
        if (lot == null) {
            return null;
        }
        
        InventoryLotDetailDTO dto = new InventoryLotDetailDTO();
        dto.setId(lot.getId());
        dto.setLotNumber(lot.getLotNumber());
        dto.setEntryDate(lot.getEntryDate());
        dto.setExpirationDate(lot.getExpirationDate());
        dto.setInitialQuantity(lot.getQuantity());
        dto.setCurrentQuantity(currentQuantity);
        dto.setTotalMoved(totalMoved);
        dto.setMovements(movements);
        
        if (lot.getProduct() != null) {
            dto.setProductId(lot.getProduct().getId());
            dto.setProductName(lot.getProduct().getName());
        }
        
        // Calcular status de expiração
        LocalDate today = LocalDate.now();
        dto.setIsExpired(lot.getExpirationDate().isBefore(today));
        
        if (!dto.getIsExpired()) {
            int daysUntil = (int) java.time.temporal.ChronoUnit.DAYS.between(today, lot.getExpirationDate());
            dto.setDaysUntilExpiration(daysUntil);
        }
        
        return dto;
    }
}

