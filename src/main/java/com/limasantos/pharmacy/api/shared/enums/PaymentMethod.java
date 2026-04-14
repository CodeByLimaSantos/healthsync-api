package com.limasantos.pharmacy.api.shared.enums;


//used by both sales and financial modules, so it is in the shared package

public enum PaymentMethod {
    CREDIT_CARD("Cartão de Crédito"),
    DEBIT_CARD("Cartão de Débito"),
    CASH("Dinheiro"),
    BANK_TRANSFER("Transferência Bancária"),
    PIX("PIX"),
    BOLETO("Boleto");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

