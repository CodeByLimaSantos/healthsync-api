package com.limasantos.pharmacy.api.customer.service;

import com.limasantos.pharmacy.api.customer.dto.CreateCustomerDTO;
import com.limasantos.pharmacy.api.customer.dto.CustomerDTO;
import com.limasantos.pharmacy.api.customer.dto.CustomerDetailDTO;
import com.limasantos.pharmacy.api.customer.entity.Customer;
import com.limasantos.pharmacy.api.customer.mapper.CustomerMapper;
import com.limasantos.pharmacy.api.customer.repository.CustomerRepository;
import com.limasantos.pharmacy.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {



    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }
    
    /**
     * Cria um novo cliente
     */
    public CustomerDTO createCustomer(CreateCustomerDTO dto) {
        // Validar CPF duplicado
        if (customerRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema");
        }
        
        Customer customer = customerMapper.convertToEntity(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.convertToBasicDTO(savedCustomer);
    }
    
    /**
     * Busca um cliente por ID
     */
    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        return customerMapper.convertToBasicDTO(customer);
    }
    
    /**
     * Busca detalhes completos de um cliente (com histórico de compras)
     */
    @Transactional(readOnly = true)
    public CustomerDetailDTO findDetailById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        return customerMapper.convertToDetailDTO(customer);
    }
    
    /**
     * Lista todos os clientes
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.convertToBasicDTOList(customers);
    }
    
    /**
     * Atualiza dados de um cliente
     */
    public CustomerDTO update(Long id, CreateCustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        // Validar CPF duplicado (se foi alterado)
        if (!customer.getCpf().equals(dto.getCpf()) && 
            customerRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema");
        }
        
        customer.setName(dto.getName());
        customer.setCpf(dto.getCpf());
        
        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.convertToBasicDTO(updatedCustomer);
    }
    
    /**
     * Deleta um cliente
     */
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        // Validar se o cliente tem vendas
        if (!customer.getPurchaseHistory().isEmpty()) {
            throw new IllegalStateException("Não é possível deletar cliente com histórico de compras");
        }
        
        customerRepository.delete(customer);
    }
    
    /**
     * Busca cliente por CPF
     */
    @Transactional(readOnly = true)
    public CustomerDTO findByCpf(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com CPF: " + cpf));
        return customerMapper.convertToBasicDTO(customer);
    }
}

