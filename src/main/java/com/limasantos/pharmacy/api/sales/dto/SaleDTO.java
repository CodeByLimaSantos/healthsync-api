package com.limasantos.pharmacy.api.sales.dto;

import com.limasantos.pharmacy.api.shared.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private List<SaleItemDTO> items;
}

