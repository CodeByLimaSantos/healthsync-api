package com.limasantos.pharmacy.api.shared.enums;

public enum MovementType {
    ENTRY("Entrada", "Entrada de mercadoria por compra ou transferência"),
    SALE_EXIT("Saída por Venda", "Saída de mercadoria por venda ao cliente"),
    ADJUSTMENT_IN("Ajuste de Entrada", "Ajuste positivo de contagem de inventário"),
    ADJUSTMENT_OUT("Ajuste de Saída", "Ajuste negativo de contagem de inventário"),
    DISPOSAL("Descarte", "Descarte por vencimento, avaria ou perda");

    private final String displayName;
    private final String description;

    MovementType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Indica se este tipo de movimentação exige motivo obrigatório.
     * Movimentações que representam perda ou ajuste negativo devem ter rastreabilidade.
     * @return true se reason é obrigatório
     */
    public boolean requiresReason() {
        return this == ADJUSTMENT_OUT || this == DISPOSAL;
    }
}
