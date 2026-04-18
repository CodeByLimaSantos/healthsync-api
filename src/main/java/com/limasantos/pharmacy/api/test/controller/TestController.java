package com.limasantos.pharmacy.api.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Testes", description = "Endpoints de validacao rapida para conferir disponibilidade e comportamento da API")
public class TestController {

    @GetMapping("/health")
    @Operation(
            summary = "Verifica saude da API",
            description = "Retorna status da API com timestamp para validar disponibilidade do servico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API disponivel",
                    content = @Content(schema = @Schema(implementation = HealthResponse.class))),
            @ApiResponse(responseCode = "500", description = "Falha interna no servidor")
    })
    public ResponseEntity<HealthResponse> health() {
        HealthResponse response = new HealthResponse("UP", OffsetDateTime.now().toString(), "HealthSync API");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/greet/{name}")
    @Operation(
            summary = "Gera saudacao personalizada",
            description = "Recebe um nome e retorna uma saudacao; pode ser normal ou em caixa alta."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saudacao gerada com sucesso",
                    content = @Content(schema = @Schema(implementation = GreetingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Nome invalido")
    })
    public ResponseEntity<GreetingResponse> greet(
            @Parameter(description = "Nome para compor a saudacao", example = "Lucas", required = true)
            @PathVariable String name,
            @Parameter(description = "Define se a mensagem sera retornada em letras maiusculas", example = "false")
            @RequestParam(defaultValue = "false") boolean uppercase
    ) {
        String greeting = "Ola, " + name + "! Bem-vindo(a) ao HealthSync.";
        if (uppercase) {
            greeting = greeting.toUpperCase();
        }

        return ResponseEntity.ok(new GreetingResponse(greeting, uppercase));
    }

    @PostMapping("/echo")
    @Operation(
            summary = "Ecoa payload de entrada",
            description = "Recebe uma mensagem no corpo da requisicao e retorna a mesma mensagem no formato padronizado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem processada com sucesso",
                    content = @Content(schema = @Schema(implementation = EchoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Payload invalido")
    })
    public ResponseEntity<EchoResponse> echo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Objeto com a mensagem a ser ecoada",
                    content = @Content(schema = @Schema(implementation = EchoRequest.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody EchoRequest request
    ) {
        EchoResponse response = new EchoResponse(request.message(), OffsetDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @Schema(name = "HealthResponse", description = "Modelo de resposta para verificar disponibilidade da API")
    public record HealthResponse(
            @Schema(description = "Estado atual da aplicacao", example = "UP") String status,
            @Schema(description = "Data e hora da verificacao", example = "2026-04-18T14:00:00Z") String timestamp,
            @Schema(description = "Identificacao do servico", example = "HealthSync API") String service
    ) {
    }

    @Schema(name = "GreetingResponse", description = "Modelo de resposta de saudacao")
    public record GreetingResponse(
            @Schema(description = "Mensagem gerada pelo endpoint", example = "Ola, Lucas! Bem-vindo(a) ao HealthSync.") String message,
            @Schema(description = "Indica se a mensagem foi convertida para maiusculas", example = "false") boolean uppercase
    ) {
    }

    @Schema(name = "EchoRequest", description = "Payload para endpoint de eco")
    public record EchoRequest(
            @Schema(description = "Mensagem enviada para eco", example = "Documentacao OpenAPI funcionando")
            @NotBlank(message = "A mensagem nao pode ser vazia")
            @Size(max = 300, message = "A mensagem deve ter no maximo 300 caracteres")
            String message
    ) {
    }

    @Schema(name = "EchoResponse", description = "Resposta do endpoint de eco")
    public record EchoResponse(
            @Schema(description = "Mensagem retornada pela API", example = "Documentacao OpenAPI funcionando") String message,
            @Schema(description = "Momento em que a API processou o payload", example = "2026-04-18T14:02:00Z") String processedAt
    ) {
    }
}

