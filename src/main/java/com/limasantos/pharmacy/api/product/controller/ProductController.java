package com.limasantos.pharmacy.api.product.controller;

import com.limasantos.pharmacy.api.dto.response.base.ApiResponse;
import com.limasantos.pharmacy.api.dto.response.base.DeleteResponse;
import com.limasantos.pharmacy.api.dto.response.base.PaginatedResponse;
import com.limasantos.pharmacy.api.dto.response.domain.product.ProductListResponse;
import com.limasantos.pharmacy.api.dto.response.domain.product.ProductResponse;
import com.limasantos.pharmacy.api.product.dto.CreateProductDTO;
import com.limasantos.pharmacy.api.product.dto.ProductDTO;
import com.limasantos.pharmacy.api.product.dto.ProductDetailDTO;
import com.limasantos.pharmacy.api.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = "Produtos", description = "Cadastro de produtos, filtros por fornecedor e controle de medicamentos")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }





    //create product
    @PostMapping("/create")
    @Operation(summary = "Criar produto", description = "Cadastra um novo produto com dados comerciais e categoria.")
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody CreateProductDTO dto,
            HttpServletRequest request) {

        ProductDTO created = productService.createProduct(dto);


        return createdResponse(
                "Produto criado com sucesso",
                "PRODUCT_CREATED",
                ProductResponse.fromDto(created),
                request


        );


    }




    //search all products
    @GetMapping("/search")
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos em envelope paginado padrão.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos",
                    content = @Content(schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse<ProductListResponse>> findAll(
            HttpServletRequest request) {

        List<ProductListResponse> products = productService.findAll().stream()
                .map(ProductListResponse::fromDto)
                .toList();

        return okPaginatedResponse(
                "Produtos recuperados com sucesso",
                "PRODUCTS_FOUND",
                products, request);

    }





    //search product by id
    @GetMapping("/search/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados resumidos de um produto específico.")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(
            @PathVariable Long id,
            HttpServletRequest request) {

        ProductDTO product = productService.findById(id);

        return okResponse(
                "Produto encontrado",
                "PRODUCT_FOUND",
                ProductResponse.fromDto(product),
                request

        );

    }





   //search product details by id
    @GetMapping("/searchDetails/{id}")
    @Operation(summary = "Buscar detalhes do produto", description = "Retorna dados detalhados incluindo margem e movimentos de estoque.")
    public ResponseEntity<ApiResponse<ProductDetailDTO>> findDetailById(
            @PathVariable Long id,
            HttpServletRequest request) {

        ProductDetailDTO detail = productService.findDetailById(id);

        return okResponse(
                "Detalhes do produto encontrados",
                "PRODUCT_DETAIL_FOUND",
                detail,
                request

        );


    }




    //search products by supplier
    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Listar produtos por fornecedor", description = "Filtra produtos vinculados a um fornecedor específico.")
    public ResponseEntity<PaginatedResponse<ProductListResponse>> findBySupplier(
            @PathVariable Long supplierId,
            HttpServletRequest request) {

        List<ProductListResponse> products = productService.findBySupplier(supplierId).stream()
                .map(ProductListResponse::fromDto)
                .toList();

        return okPaginatedResponse("Produtos do fornecedor recuperados",
                                      "SUPPLIER_PRODUCTS_FOUND",
                                            products, request);

    }






    //search controlled products
    @GetMapping("/controlled")
    @Operation(summary = "Listar produtos controlados", description = "Retorna apenas produtos com controle especial.")
    public ResponseEntity<PaginatedResponse<ProductListResponse>> findControlledProducts(HttpServletRequest request) {

        List<ProductListResponse> products = productService.findControlledProducts().stream()
                .map(ProductListResponse::fromDto)
                .toList();


        return okPaginatedResponse("Produtos controlados recuperados", "CONTROLLED_PRODUCTS_FOUND", products, request);
    }






    //update product
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente.")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductDTO dto,
            HttpServletRequest request) {

        ProductDTO updated = productService.update(id, dto);

        return okResponse(
                "Produto atualizado com sucesso",
                "PRODUCT_UPDATED",
                ProductResponse.fromDto(updated),
                request

        );



    }





    //delete product
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Excluir produto", description = "Exclui um produto e retorna confirmação da operação.")
    public ResponseEntity<ApiResponse<DeleteResponse>> delete(
            @PathVariable Long id,
            HttpServletRequest request) {

        productService.delete(id);
        return okResponse(
                "Produto deletado com sucesso",
                "PRODUCT_DELETED",
                new DeleteResponse(id, true),
                request
        );
    }






// Methods to build responses

    private <T> ResponseEntity<ApiResponse<T>> createdResponse(
            String message,
            String code,
            T data,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(buildApiResponse(message, code, data, request));
    }

    private <T> ResponseEntity<ApiResponse<T>> okResponse(
            String message,
            String code,
            T data,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .ok(buildApiResponse(message, code, data, request));
    }

    private <T> ApiResponse<T> buildApiResponse(
            String message,
            String code,
            T data,
            HttpServletRequest request
    ) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .code(code)
                .timestamp(LocalDateTime.now())
                .path(resolvePath(request))
                .data(data)
                .build();
    }

    private <T> ResponseEntity<PaginatedResponse<T>> okPaginatedResponse(
            String message,
            String code,
            List<T> data,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                PaginatedResponse.<T>paginatedBuilder()
                        .success(true)
                        .message(message)
                        .code(code)
                        .timestamp(LocalDateTime.now())
                        .path(resolvePath(request))
                        .data(data)
                        .page(0)
                        .pageSize(data.size())
                        .totalElements(data.size())
                        .totalPages(data.isEmpty() ? 0 : 1)
                        .hasNext(false)
                        .hasPrevious(false)
                        .build()
        );
    }

    private String resolvePath(HttpServletRequest request) {
        String queryString = request.getQueryString();

        return (queryString == null)
                ? request.getRequestURI()
                : request.getRequestURI() + "?" + queryString;
    }
    }