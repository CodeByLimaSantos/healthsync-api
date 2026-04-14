package com.limasantos.pharmacy.api.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerPurchaseHistoryDTO {
    private Long saleId;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private String paymentMethod;
}

