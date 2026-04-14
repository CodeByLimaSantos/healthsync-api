package com.limasantos.pharmacy.api.financial.mapper;

import com.limasantos.pharmacy.api.financial.dto.CreateFinancialDTO;
import com.limasantos.pharmacy.api.financial.dto.FinancialDTO;
import com.limasantos.pharmacy.api.financial.dto.FinancialDetailDTO;
import com.limasantos.pharmacy.api.financial.entity.Financial;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FinancialMapper {


    //transformar entidade em DTO para listagem
    public FinancialDTO toDTO(Financial financial) {
        if (financial == null) {
            return null;
        }
        
        FinancialDTO dto = new FinancialDTO();
        dto.setId(financial.getId());
        dto.setType(financial.getType());
        dto.setDescription(financial.getDescription());
        dto.setAmount(financial.getAmount());
        dto.setIssueDate(financial.getIssueDate());
        dto.setDueDate(financial.getDueDate());
        dto.setPaymentDate(financial.getPaymentDate());
        dto.setStatus(financial.getStatus());
        dto.setPaymentMethod(financial.getPaymentMethod());
        dto.setNotes(financial.getNotes());
        
        if (financial.getCustomer() != null) {
            dto.setCustomerId(financial.getCustomer().getId());
            dto.setCustomerName(financial.getCustomer().getName());
        }
        
        if (financial.getSupplier() != null) {
            dto.setSupplierId(financial.getSupplier().getId());
            dto.setSupplierName(financial.getSupplier().getName());
        }
        
        return dto;
    }

    // transformar lista de entidades em lista de DTOs
    public List<FinancialDTO> toDTOList(List<Financial> financials) {
        return financials.stream()
                .map(this::toDTO)
                .toList();
    }

    //dto para criar nova entidade
    public Financial toEntity(CreateFinancialDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Financial financial = new Financial();
        financial.setType(dto.getType());
        financial.setDescription(dto.getDescription());
        financial.setAmount(dto.getAmount());
        financial.setDueDate(dto.getDueDate());
        financial.setNotes(dto.getNotes());
        
        return financial;
    }

    // transformar entidade em DTO detalhado para visualização individual
    public FinancialDetailDTO convertToDetailDTO(Financial financial) {
        if (financial == null) {
            return null;
        }
        
        FinancialDetailDTO dto = new FinancialDetailDTO();
        dto.setId(financial.getId());
        dto.setType(financial.getType());
        dto.setDescription(financial.getDescription());
        dto.setAmount(financial.getAmount());
        dto.setIssueDate(financial.getIssueDate());
        dto.setDueDate(financial.getDueDate());
        dto.setPaymentDate(financial.getPaymentDate());
        dto.setStatus(financial.getStatus());
        dto.setPaymentMethod(financial.getPaymentMethod());
        dto.setNotes(financial.getNotes());
        dto.setCreatedAt(financial.getCreatedAt());
        dto.setUpdatedAt(financial.getUpdatedAt());
        
        if (financial.getCustomer() != null) {
            dto.setCustomerId(financial.getCustomer().getId());
            dto.setCustomerName(financial.getCustomer().getName());
            dto.setCustomerCpf(financial.getCustomer().getCpf());
        }
        
        if (financial.getSupplier() != null) {
            dto.setSupplierId(financial.getSupplier().getId());
            dto.setSupplierName(financial.getSupplier().getName());
            dto.setSupplierCnpj(financial.getSupplier().getCnpj());
        }
        
        // Calcular se está vencido
        dto.setIsOverdue(financial.isOverdue());
        
        // Calcular dias até vencimento (negativo se vencido)
        LocalDate today = LocalDate.now();
        int daysUntilDue = (int) java.time.temporal.ChronoUnit.DAYS.between(today, financial.getDueDate());
        dto.setDaysUntilDue(daysUntilDue);
        
        return dto;
    }
}

