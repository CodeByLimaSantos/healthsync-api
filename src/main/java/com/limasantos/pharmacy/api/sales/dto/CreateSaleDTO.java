package com.limasantos.pharmacy.api.sales.dto;

import com.limasantos.pharmacy.api.shared.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSaleDTO {
    
    private Long customerId; // Opcional - pode ser venda sem cliente cadastrado
    
    @NotNull(message = "Método de pagamento é obrigatório")
    private PaymentMethod paymentMethod;
    
    @NotEmpty(message = "A venda deve conter pelo menos um item")
    @Valid
    private List<CreateSaleItemDTO> items;
}

