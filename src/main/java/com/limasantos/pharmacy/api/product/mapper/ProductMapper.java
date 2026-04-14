package com.limasantos.pharmacy.api.product.mapper;

import com.limasantos.pharmacy.api.product.dto.CreateProductDTO;
import com.limasantos.pharmacy.api.product.dto.ProductDTO;
import com.limasantos.pharmacy.api.product.dto.ProductDetailDTO;
import com.limasantos.pharmacy.api.product.dto.ProductMovementDTO;
import com.limasantos.pharmacy.api.product.entity.Product;
import com.limasantos.pharmacy.api.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductMapper {
    
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPriceCost(product.getPriceCost());
        dto.setPriceSale(product.getPriceSale());
        dto.setControlled(product.getControlled());
        dto.setTarja(product.getTarja());
        dto.setRegisterMS(product.getRegisterMS());
        dto.setProductCategoryType(product.getProductCategoryType());
        
        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getId());
            dto.setSupplierName(product.getSupplier().getName());
        }
        
        return dto;
    }
    
    public List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(this::toDTO)
                .toList();
    }
    
    public Product toEntity(CreateProductDTO dto, Supplier supplier) {
        if (dto == null) {
            return null;
        }
        
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPriceCost(dto.getPriceCost());
        product.setPriceSale(dto.getPriceSale());
        product.setControlled(dto.getControlled());
        product.setTarja(dto.getTarja());
        product.setRegisterMS(dto.getRegisterMS());
        product.setProductCategoryType(dto.getProductCategoryType());
        product.setSupplier(supplier);
        
        return product;
    }
    
    public ProductDetailDTO convertToDetailDTO(Product product, Integer currentStock) {
        if (product == null) {
            return null;
        }
        
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPriceCost(product.getPriceCost());
        dto.setPriceSale(product.getPriceSale());
        dto.setControlled(product.getControlled());
        dto.setTarja(product.getTarja());
        dto.setRegisterMS(product.getRegisterMS());
        dto.setProductCategoryType(product.getProductCategoryType());
        dto.setCurrentStock(currentStock);
        
        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getId());
            dto.setSupplierName(product.getSupplier().getName());
        }
        
        // Calcular margem de lucro
        if (product.getPriceCost() != null && product.getPriceSale() != null) {
            BigDecimal margin = product.getPriceSale().subtract(product.getPriceCost());
            dto.setMargin(margin);
        }
        
        return dto;
    }
}

