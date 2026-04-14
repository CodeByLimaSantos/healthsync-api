package com.limasantos.pharmacy.api.product.service;

import com.limasantos.pharmacy.api.inventory.service.InventoryService;
import com.limasantos.pharmacy.api.product.dto.CreateProductDTO;
import com.limasantos.pharmacy.api.product.dto.ProductDTO;
import com.limasantos.pharmacy.api.product.dto.ProductDetailDTO;
import com.limasantos.pharmacy.api.product.entity.Product;
import com.limasantos.pharmacy.api.product.mapper.ProductMapper;
import com.limasantos.pharmacy.api.product.repository.ProductRepository;
import com.limasantos.pharmacy.api.shared.exception.ResourceNotFoundException;
import com.limasantos.pharmacy.api.supplier.entity.Supplier;
import com.limasantos.pharmacy.api.supplier.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final InventoryService inventoryService;


    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository,
                          ProductMapper productMapper, InventoryService inventoryService) {

        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productMapper = productMapper;
        this.inventoryService = inventoryService;

    }



    // ---> CREATE

    public ProductDTO createProduct(CreateProductDTO dto) {
        Supplier supplier = getSupplierOrThrow(dto.getSupplierId());
        validatePrices(dto);

        Product product = productMapper.toEntity(dto, supplier);
        Product saved = productRepository.save(product);

        return productMapper.toDTO(saved);
    }



    // ---> READ

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Product product = getProductOrThrow(id);
        return productMapper.toDTO(product);

    }

    @Transactional(readOnly = true)
    public ProductDetailDTO findDetailById(Long id) {
        Product product = getProductOrThrow(id);
        Integer stock = inventoryService.calculateProductStock(id);

        return productMapper.convertToDetailDTO(product, stock);
    }


    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {

        return productMapper.toDTOList(productRepository.findAll());

    }

    // ================= UPDATE =================
    public ProductDTO update(Long id, CreateProductDTO dto) {
        Product product = getProductOrThrow(id);
        Supplier supplier = getSupplierOrThrow(dto.getSupplierId());

        validatePrices(dto);

        updateProductFields(product, dto, supplier);

        Product updated = productRepository.save(product);
        return productMapper.toDTO(updated);
    }

    // ================= DELETE =================
    public void delete(Long id) {
        Product product = getProductOrThrow(id);

        Integer stock = inventoryService.calculateProductStock(id);
        if (stock > 0) {
            throw new IllegalStateException("Não é possível deletar produto com estoque disponível");
        }

        productRepository.delete(product);
    }


    // ================= FILTERS =================
    @Transactional(readOnly = true)
    public List<ProductDTO> findBySupplier(Long supplierId) {
        Supplier supplier = getSupplierOrThrow(supplierId);
        return productMapper.toDTOList(productRepository.findBySupplier(supplier));
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findControlledProducts() {
        return productMapper.toDTOList(productRepository.findByControlledTrue());
    }

    // ================= PRIVATE METHODS =================

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    private Supplier getSupplierOrThrow(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));
    }

    private void validatePrices(CreateProductDTO dto) {
        if (dto.getPriceSale().compareTo(dto.getPriceCost()) <= 0) {
            throw new IllegalArgumentException("Preço de venda deve ser maior que o preço de custo");
        }
    }

    private void updateProductFields(Product product, CreateProductDTO dto, Supplier supplier) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPriceCost(dto.getPriceCost());
        product.setPriceSale(dto.getPriceSale());
        product.setControlled(dto.getControlled());
        product.setTarja(dto.getTarja());
        product.setRegisterMS(dto.getRegisterMS());
        product.setProductCategoryType(dto.getProductCategoryType());
        product.setSupplier(supplier);
    }
}