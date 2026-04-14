package com.limasantos.pharmacy.api.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSaleItemDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    private Long productId;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantity;

    @NotNull(message = "O preço de venda é obrigatório")
    @PositiveOrZero(message = "O preço não pode ser negativo")
    private BigDecimal priceAtSale;
}

