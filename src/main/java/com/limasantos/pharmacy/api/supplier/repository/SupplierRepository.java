package com.limasantos.pharmacy.api.supplier.repository;

import com.limasantos.pharmacy.api.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    /**
     * Busca fornecedor por CNPJ
     */
    Optional<Supplier> findByCnpj(String cnpj);
    
    /**
     * Verifica se CNPJ existe
     */
    boolean existsByCnpj(String cnpj);
    
    /**
     * Busca fornecedores por nome (case-insensitive)
     */
    List<Supplier> findByNameContainingIgnoreCase(String name);
}

