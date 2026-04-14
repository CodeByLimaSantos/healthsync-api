package com.limasantos.pharmacy.api.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDetailDTO {
    private Long id;
    private String name;
    private String cpf;
    private List<CustomerPurchaseHistoryDTO> purchaseHistory;
    private BigDecimal totalSpent;
    private Integer totalPurchases;
}

