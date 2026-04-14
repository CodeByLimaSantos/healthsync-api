package com.limasantos.pharmacy.api.sales.mapper;

import com.limasantos.pharmacy.api.sales.dto.CreateSaleDTO;
import com.limasantos.pharmacy.api.sales.dto.SaleDTO;
import com.limasantos.pharmacy.api.sales.dto.SaleDetailDTO;
import com.limasantos.pharmacy.api.sales.entity.Sale;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleMapper {
    
    private final SaleItemMapper saleItemMapper;
    
    public SaleMapper(SaleItemMapper saleItemMapper) {
        this.saleItemMapper = saleItemMapper;
    }
    
    // Converte Sale para DTO básico (simples)
    public SaleDTO convertToBasicDTO(Sale sale) {
        if (sale == null) {
            return null;
        }
        
        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setPaymentMethod(sale.getPaymentMethod());
        
        if (sale.getCustomer() != null) {
            dto.setCustomerId(sale.getCustomer().getId());
            dto.setCustomerName(sale.getCustomer().getName());
        }
        
        dto.setItems(saleItemMapper.convertToBasicDTOList(sale.getItems()));
        
        return dto;
    }
    
    // Converte lista de Sale para lista de DTO básico (simples)
    public List<SaleDTO> convertToBasicDTOList(List<Sale> sales) {
        return sales.stream()
                .map(this::convertToBasicDTO)
                .toList();
    }
    
    // Cria Sale a partir de DTO de criação
    public Sale convertToEntity(CreateSaleDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Sale sale = new Sale();
        sale.setPaymentMethod(dto.getPaymentMethod());
        
        return sale;
    }
    
    // Converte Sale para DTO detalhado (com dados do cliente e itens detalhados)
    public SaleDetailDTO convertToDetailDTO(Sale sale) {
        if (sale == null) {
            return null;
        }
        
        SaleDetailDTO dto = new SaleDetailDTO();
        dto.setId(sale.getId());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setPaymentMethod(sale.getPaymentMethod());
        dto.setItemsCount(sale.getItems().size());
        
        if (sale.getCustomer() != null) {
            dto.setCustomerId(sale.getCustomer().getId());
            dto.setCustomerName(sale.getCustomer().getName());
            dto.setCustomerCpf(sale.getCustomer().getCpf());
        }
        
        dto.setItems(saleItemMapper.convertToDetailDTOList(sale.getItems()));
        
        return dto;
    }
}

