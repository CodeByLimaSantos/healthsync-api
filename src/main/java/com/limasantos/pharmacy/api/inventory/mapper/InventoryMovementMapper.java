package com.limasantos.pharmacy.api.inventory.mapper;

import com.limasantos.pharmacy.api.inventory.dto.CreateInventoryMovementDTO;
import com.limasantos.pharmacy.api.inventory.dto.InventoryMovementDTO;
import com.limasantos.pharmacy.api.inventory.entity.InventoryMovements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryMovementMapper {
    
    public InventoryMovementDTO toDTO(InventoryMovements movement) {
        if (movement == null) {
            return null;
        }
        
        InventoryMovementDTO dto = new InventoryMovementDTO();
        dto.setId(movement.getId());
        dto.setMovementType(movement.getMovementType());
        dto.setQuantity(movement.getQuantity());
        dto.setMovementDate(movement.getMovementDate());
        dto.setReason(movement.getReason());
        
        if (movement.getInventoryLot() != null) {
            dto.setInventoryLotId(movement.getInventoryLot().getId());
            dto.setLotNumber(movement.getInventoryLot().getLotNumber());
            
            if (movement.getInventoryLot().getProduct() != null) {
                dto.setProductId(movement.getInventoryLot().getProduct().getId());
                dto.setProductName(movement.getInventoryLot().getProduct().getName());
            }
        }
        
        return dto;
    }
    
    public List<InventoryMovementDTO> toDTOList(List<InventoryMovements> movements) {
        return movements.stream()
                .map(this::toDTO)
                .toList();
    }
    
    public InventoryMovements convertToEntity(CreateInventoryMovementDTO dto) {
        if (dto == null) {
            return null;
        }
        
        InventoryMovements movement = new InventoryMovements();
        movement.setMovementType(dto.getMovementType());
        movement.setQuantity(dto.getQuantity());
        movement.setReason(dto.getReason());
        
        return movement;
    }
}

