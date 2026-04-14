package com.limasantos.pharmacy.api.financial.entity;

import com.limasantos.pharmacy.api.customer.entity.Customer;
import com.limasantos.pharmacy.api.shared.enums.PaymentMethod;
import com.limasantos.pharmacy.api.supplier.entity.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_financials")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Financial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de lançamento: CONTA_A_RECEBER ou CONTA_A_PAGAR
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinancialType type;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal amount;

    // Data de emissão/criação
    @Column(nullable = false, updatable = false)
    private LocalDateTime issueDate;

    // Data de vencimento
    @NotNull
    @Column(nullable = false)
    private LocalDate dueDate;

    // Data de pagamento (null se não pago)
    private LocalDate paymentDate;

    // Status do lançamento
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    // Método de pagamento realizado
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // Relacionamento com Cliente (para contas a receber)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Relacionamento com Fornecedor (para contas a pagar)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Rastreamento de criação/atualização
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    // Enums com descrições internas ao Financial para facilitar a manutenção e evitar dependências externas
    public enum FinancialType {
        CONTA_A_RECEBER("Conta a Receber"),
        CONTA_A_PAGAR("Conta a Pagar");

        private final String description;

        FinancialType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PaymentStatus {
        PENDING("Pendente"),
        PARTIALLY_PAID("Parcialmente Pago"),
        PAID("Pago"),
        OVERDUE("Vencido"),
        CANCELED("Cancelado");

        private final String description;

        PaymentStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


    @PrePersist
    public void prePersist() {
        this.issueDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = PaymentStatus.PENDING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPaid(PaymentMethod method) {
        if (this.status == PaymentStatus.PAID) {
            throw new IllegalStateException("Pagamento já realizado");
        }
        this.paymentDate = LocalDate.now();
        this.paymentMethod = method;
        this.status = PaymentStatus.PAID;
    }

    public boolean isOverdue() {
        return this.status == PaymentStatus.OVERDUE || 
               (LocalDate.now().isAfter(this.dueDate) &&
                (this.status == PaymentStatus.PENDING || this.status == PaymentStatus.PARTIALLY_PAID));
    }
}