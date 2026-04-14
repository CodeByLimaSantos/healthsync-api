package com.limasantos.pharmacy.api.customer.repository;

import com.limasantos.pharmacy.api.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Busca cliente por CPF
     */
    Optional<Customer> findByCpf(String cpf);

    /**
     * Verifica se CPF existe
     */
    boolean existsByCpf(String cpf);

    /**
     * Busca clientes por nome (case-insensitive)
     */
    java.util.List<Customer> findByNameContainingIgnoreCase(String name);
}

