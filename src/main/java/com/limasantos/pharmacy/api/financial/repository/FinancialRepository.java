package com.limasantos.pharmacy.api.financial.repository;

import com.limasantos.pharmacy.api.financial.entity.Financial;
import com.limasantos.pharmacy.api.financial.entity.Financial.FinancialType;
import com.limasantos.pharmacy.api.financial.entity.Financial.PaymentStatus;
import com.limasantos.pharmacy.api.customer.entity.Customer;
import com.limasantos.pharmacy.api.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, Long> {
    
    /**
     * Busca lançamentos por tipo
     */
    List<Financial> findByType(FinancialType type);
    
    /**
     * Busca lançamentos por status
     */
    List<Financial> findByStatus(PaymentStatus status);
    
    /**
     * Busca lançamentos de um cliente
     */
    List<Financial> findByCustomer(Customer customer);
    
    /**
     * Busca lançamentos de um fornecedor
     */
    List<Financial> findBySupplier(Supplier supplier);
    
    /**
     * Busca lançamentos vencidos
     */
    List<Financial> findByDueDateBefore(LocalDate date);
    
    /**
     * Busca lançamentos vencendo em um período
     */
    List<Financial> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Busca lançamentos por período de emissão
     */
    List<Financial> findByIssueDateBetween(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
    
    /**
     * Conta lançamentos pendentes de um cliente
     */
    long countByCustomerAndStatus(Customer customer, PaymentStatus status);
    
    /**
     * Conta lançamentos vencidos
     */
    long countByDueDateBeforeAndStatus(LocalDate date, PaymentStatus status);
}

