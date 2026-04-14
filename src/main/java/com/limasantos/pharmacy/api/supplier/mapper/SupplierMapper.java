package com.limasantos.pharmacy.api.supplier.mapper;

import com.limasantos.pharmacy.api.supplier.dto.CreateSupplierDTO;
import com.limasantos.pharmacy.api.supplier.dto.SupplierDTO;
import com.limasantos.pharmacy.api.supplier.dto.SupplierDetailDTO;
import com.limasantos.pharmacy.api.supplier.dto.SupplierProductDTO;
import com.limasantos.pharmacy.api.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplierMapper {
    
    public SupplierDTO toDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setCnpj(supplier.getCnpj());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        
        return dto;
    }
    
    public List<SupplierDTO> toDTOList(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(this::toDTO)
                .toList();
    }
    
    public Supplier toEntity(CreateSupplierDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setCnpj(dto.getCnpj());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        
        return supplier;
    }
    
    public SupplierDetailDTO toDetailDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        
        SupplierDetailDTO dto = new SupplierDetailDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setCnpj(supplier.getCnpj());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setProductsCount(supplier.getProducts().size());
        
        // Mapear produtos do fornecedor
        List<SupplierProductDTO> products = supplier.getProducts()
                .stream()
                .map(product -> new SupplierProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getId().toString(), // SKU pode ser o ID ou outro campo
                        product.getProductCategoryType(),
                        product.getPriceCost(),
                        product.getPriceSale(),
                        product.getControlled()
                ))
                .toList();
        
        dto.setProducts(products);
        
        return dto;
    }
}

