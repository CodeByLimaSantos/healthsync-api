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

    @NotBlank(message = "Nome do cliente é obrigatório")
    private String name;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
}


//dto para criar novo cliente, contendo os campos necessários para a criação de um cliente,
// como nome e CPF. Ele é usado para receber os dados do cliente em endpoints de criação.

