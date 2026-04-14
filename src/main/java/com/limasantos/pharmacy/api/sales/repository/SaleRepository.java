package com.limasantos.pharmacy.api.sales.repository;

import com.limasantos.pharmacy.api.sales.entity.Sale;
import com.limasantos.pharmacy.api.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    
    /**
     * Busca vendas de um cliente
     */
    List<Sale> findByCustomer(Customer customer);
    
    /**
     * Busca vendas de um cliente nulo (vendas sem cliente)
     */
    List<Sale> findByCustomerIsNull();
    
    /**
     * Busca vendas por período
     */
    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Conta vendas de um cliente
     */
    long countByCustomer(Customer customer);
}

