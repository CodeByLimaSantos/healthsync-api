package com.limasantos.pharmacy.api.sales.mapper;

import com.limasantos.pharmacy.api.sales.dto.CreateSaleItemDTO;
import com.limasantos.pharmacy.api.sales.dto.SaleItemDTO;
import com.limasantos.pharmacy.api.sales.dto.SaleItemDetailDTO;
import com.limasantos.pharmacy.api.sales.entity.SaleItem;
import com.limasantos.pharmacy.api.product.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SaleItemMapper {
    
    // Converte SaleItem para DTO básico (simples)
    public SaleItemDTO convertToBasicDTO(SaleItem item) {
        if (item == null) {
            return null;
        }
        
        SaleItemDTO dto = new SaleItemDTO();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtSale(item.getPriceAtSale());
        
        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
        }
        
        // Calcular subtotal
        if (item.getPriceAtSale() != null) {
            BigDecimal subtotal = item.getPriceAtSale().multiply(BigDecimal.valueOf(item.getQuantity()));
            dto.setSubtotal(subtotal);
        }
        
        return dto;
    }
    
    // Converte lista de SaleItem para lista de DTO básico (simples)
    public List<SaleItemDTO> convertToBasicDTOList(List<SaleItem> items) {
        return items.stream()
                .map(this::convertToBasicDTO)
                .toList();
    }
    
    // Converte SaleItem para DTO detalhado (com descrição e margem de lucro)
    public SaleItemDetailDTO convertToDetailDTO(SaleItem item) {
        if (item == null) {
            return null;
        }
        
        SaleItemDetailDTO dto = new SaleItemDetailDTO();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtSale(item.getPriceAtSale());
        
        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductDescription(item.getProduct().getDescription());
        }
        
        // Calcular subtotal
        if (item.getPriceAtSale() != null) {
            BigDecimal subtotal = item.getPriceAtSale().multiply(BigDecimal.valueOf(item.getQuantity()));
            dto.setSubtotal(subtotal);
            
            // Calcular margem (lucro)
            if (item.getProduct() != null && item.getProduct().getPriceCost() != null) {
                BigDecimal marginPerUnit = item.getPriceAtSale().subtract(item.getProduct().getPriceCost());
                BigDecimal margin = marginPerUnit.multiply(BigDecimal.valueOf(item.getQuantity()));
                dto.setMargin(margin);
            }
        }
        
        return dto;
    }
    
    // Converte lista de SaleItem para lista de DTO detalhado
    public List<SaleItemDetailDTO> convertToDetailDTOList(List<SaleItem> items) {
        return items.stream()
                .map(this::convertToDetailDTO)
                .toList();
    }
    
    // Cria SaleItem a partir de DTO de criação
    public SaleItem convertToEntity(CreateSaleItemDTO dto, Product product) {
        if (dto == null) {
            return null;
        }
        
        SaleItem item = new SaleItem();
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPriceAtSale(dto.getPriceAtSale());
        
        return item;
    }
}

