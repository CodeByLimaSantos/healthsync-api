package com.limasantos.pharmacy.api.financial.dto;

import com.limasantos.pharmacy.api.financial.entity.Financial.FinancialType;
import com.limasantos.pharmacy.api.financial.entity.Financial.PaymentStatus;
import com.limasantos.pharmacy.api.shared.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialDTO {
    private Long id;
    private FinancialType type;
    private String description;
    private BigDecimal amount;
    private LocalDateTime issueDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private Long customerId;
    private String customerName;
    private Long supplierId;
    private String supplierName;
    private String notes;
}

