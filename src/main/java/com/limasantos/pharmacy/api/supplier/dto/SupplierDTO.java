package com.limasantos.pharmacy.api.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierDTO {
    private Long id;
    private String name;
    private String cnpj;
    private String email;
    private String phone;
}

