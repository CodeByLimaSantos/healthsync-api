package com.limasantos.pharmacy.api.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCustomerDTO {

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String address;

}


//dto para criar novo cliente, contendo os campos necessários para a criação de um cliente,
// como nome e CPF. Ele é usado para receber os dados do cliente em endpoints de criação.

