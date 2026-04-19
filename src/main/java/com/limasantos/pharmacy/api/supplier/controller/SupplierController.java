package com.limasantos.pharmacy.api.supplier.controller;

import com.limasantos.pharmacy.api.dto.response.base.ApiResponse;
import com.limasantos.pharmacy.api.dto.response.base.DeleteResponse;
import com.limasantos.pharmacy.api.dto.response.base.PaginatedResponse;
import com.limasantos.pharmacy.api.dto.response.domain.supplier.SupplierListResponse;
import com.limasantos.pharmacy.api.dto.response.domain.supplier.SupplierResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.limasantos.pharmacy.api.supplier.dto.CreateSupplierDTO;
import com.limasantos.pharmacy.api.supplier.dto.SupplierDTO;
import com.limasantos.pharmacy.api.supplier.dto.SupplierDetailDTO;
import com.limasantos.pharmacy.api.supplier.service.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
@Tag(name = "Fornecedores", description = "Cadastro de fornecedores e consultas por CNPJ")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }


    //create supplier
    @PostMapping("/create")
    @Operation(summary = "Criar fornecedor", description = "Cadastra um novo fornecedor no sistema.")
    public ResponseEntity<ApiResponse<SupplierResponse>> create(
            @Valid @RequestBody CreateSupplierDTO dto,
            HttpServletRequest request) {

        SupplierDTO created = supplierService.createSupplier(dto);

        return createdResponse(
                "Fornecedor criado com sucesso",
                "SUPPLIER_CREATED",
                SupplierResponse.fromDto(created),
                request

        );
    }


    //search by id
    @GetMapping("/search/{id}")
    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna dados resumidos de um fornecedor.")
    public ResponseEntity<ApiResponse<SupplierResponse>> findById(
            @PathVariable Long id,
            HttpServletRequest request) {

        SupplierDTO supplier = supplierService.findById(id);

        return okResponse(
                "Fornecedor encontrado",
                "SUPPLIER_FOUND",
                SupplierResponse.fromDto(supplier),
                request

        );
    }


    //search details by id
    @GetMapping("/searchDetails/{id}")
    @Operation(summary = "Buscar detalhes do fornecedor", description = "Retorna informações detalhadas e produtos associados.")
    public ResponseEntity<ApiResponse<SupplierDetailDTO>> findDetailById(
            @PathVariable Long id,
            HttpServletRequest request) {

        SupplierDetailDTO detail = supplierService.findDetailById(id);

        return okResponse(
                "Detalhes do fornecedor encontrados",
                "SUPPLIER_DETAIL_FOUND",
                detail,
                request

        );
    }


    //show all suppliers
    @GetMapping("all")
    @Operation(summary = "Listar fornecedores", description = "Retorna todos os fornecedores no envelope paginado padrão.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista de fornecedores",
                    content = @Content(schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse<SupplierListResponse>> findAll(HttpServletRequest request) {

        List<SupplierListResponse> suppliers = supplierService.findAll().stream()
                .map(SupplierListResponse::fromDto)
                .toList();

        return okPaginatedResponse("Fornecedores recuperados com sucesso",
                "SUPPLIERS_FOUND",
                suppliers,
                request);


    }


    //search supplier by cnpj
    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Buscar fornecedor por CNPJ", description = "Localiza um fornecedor pelo CNPJ informado.")
    public ResponseEntity<ApiResponse<SupplierResponse>> findByCnpj(
            @PathVariable String cnpj,
            HttpServletRequest request) {

        SupplierDTO supplier = supplierService.findByCnpj(cnpj);

        return okResponse(
                "Fornecedor encontrado por CNPJ",
                "SUPPLIER_FOUND_BY_CNPJ",
                SupplierResponse.fromDto(supplier),
                request


        );
    }


    //update supplier
    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar fornecedor", description = "Atualiza os dados de um fornecedor existente.")
    public ResponseEntity<ApiResponse<SupplierResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateSupplierDTO dto,
            HttpServletRequest request) {

        SupplierDTO updated = supplierService.update(id, dto);

        return okResponse(
                "Fornecedor atualizado com sucesso",
                "SUPPLIER_UPDATED",
                SupplierResponse.fromDto(updated),
                request


        );

    }


    //delete supplier
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "Excluir fornecedor", description = "Exclui um fornecedor e retorna confirmação da operação.")
    public ResponseEntity<ApiResponse<DeleteResponse>> delete(
            @PathVariable Long id,
            HttpServletRequest request) {
        supplierService.delete(id);

        return okResponse(
                "Fornecedor deletado com sucesso",
                "SUPPLIER_DELETED",
                new DeleteResponse(id, true),
                request
        );


    }









    /// Helper methods for consistent API responses

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