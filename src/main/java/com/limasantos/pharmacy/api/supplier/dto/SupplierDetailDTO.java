package com.limasantos.pharmacy.api.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierDetailDTO {
    private Long id;
    private String name;
    private String cnpj;
    private String email;
    private String phone;
    private Integer productsCount;
    private List<SupplierProductDTO> products;
}

