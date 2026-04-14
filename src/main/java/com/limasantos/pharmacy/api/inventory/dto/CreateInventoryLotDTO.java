package com.limasantos.pharmacy.api.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateInventoryLotDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    private Long productId;

    @NotBlank(message = "O número do lote é obrigatório")
    private String lotNumber;

    @NotNull(message = "A data de expiração é obrigatória")
    private LocalDate expirationDate;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser positiva")
    private Integer quantity;
}

