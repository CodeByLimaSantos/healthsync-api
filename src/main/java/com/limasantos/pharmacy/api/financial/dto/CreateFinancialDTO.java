package com.limasantos.pharmacy.api.financial.dto;

import com.limasantos.pharmacy.api.financial.entity.Financial.FinancialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFinancialDTO {

    @NotNull(message = "Tipo de lançamento é obrigatório")
    private FinancialType type;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal amount;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dueDate;

    // Para conta a receber
    private Long customerId;

    // Para conta a pagar
    private Long supplierId;

    private String notes;
}

