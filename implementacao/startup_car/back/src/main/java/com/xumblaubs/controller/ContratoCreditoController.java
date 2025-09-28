package com.xumblaubs.controller;

import com.xumblaubs.dto.ContratoCreditoRequest;
import com.xumblaubs.dto.ContratoCreditoResponse;
import com.xumblaubs.service.ContratoCreditoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratos-credito")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContratoCreditoController {

    private static final Logger logger = LoggerFactory.getLogger(ContratoCreditoController.class);

    @Autowired
    private ContratoCreditoService contratoCreditoService;

    // Listar todos os contratos
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContratoCreditoResponse>> listarTodos() {
        logger.info("Listando todos os contratos de crédito");
        try {
            List<ContratoCreditoResponse> contratos = contratoCreditoService.listarTodos();
            return ResponseEntity.ok(contratos);
        } catch (Exception e) {
            logger.error("Erro ao listar contratos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar contratos por cliente
    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ContratoCreditoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        logger.info("Listando contratos do cliente ID: {}", clienteId);
        try {
            List<ContratoCreditoResponse> contratos = contratoCreditoService.listarPorCliente(clienteId);
            return ResponseEntity.ok(contratos);
        } catch (Exception e) {
            logger.error("Erro ao listar contratos do cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar contratos por banco
    @GetMapping("/banco/{bancoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContratoCreditoResponse>> listarPorBanco(@PathVariable Long bancoId) {
        logger.info("Listando contratos do banco ID: {}", bancoId);
        try {
            List<ContratoCreditoResponse> contratos = contratoCreditoService.listarPorBanco(bancoId);
            return ResponseEntity.ok(contratos);
        } catch (Exception e) {
            logger.error("Erro ao listar contratos do banco: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar contratos pendentes
    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContratoCreditoResponse>> listarPendentes() {
        logger.info("Listando contratos pendentes");
        try {
            List<ContratoCreditoResponse> contratos = contratoCreditoService.listarPendentes();
            return ResponseEntity.ok(contratos);
        } catch (Exception e) {
            logger.error("Erro ao listar contratos pendentes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar contrato por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando contrato ID: {}", id);
        try {
            Optional<ContratoCreditoResponse> contrato = contratoCreditoService.buscarPorId(id);
            if (contrato.isPresent()) {
                return ResponseEntity.ok(contrato.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Criar novo contrato
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarContrato(@Valid @RequestBody ContratoCreditoRequest request) {
        logger.info("Criando novo contrato de crédito: {}", request);
        try {
            ContratoCreditoResponse contrato = contratoCreditoService.criarContrato(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(contrato);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar contrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao criar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Atualizar contrato
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarContrato(@PathVariable Long id, @Valid @RequestBody ContratoCreditoRequest request) {
        logger.info("Atualizando contrato ID: {} com dados: {}", id, request);
        try {
            ContratoCreditoResponse contrato = contratoCreditoService.atualizarContrato(id, request);
            return ResponseEntity.ok(contrato);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar contrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Aprovar contrato
    @PutMapping("/{id}/aprovar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> aprovarContrato(@PathVariable Long id) {
        logger.info("Aprovando contrato ID: {}", id);
        try {
            ContratoCreditoResponse contrato = contratoCreditoService.aprovarContrato(id);
            return ResponseEntity.ok(contrato);
        } catch (RuntimeException e) {
            logger.error("Erro ao aprovar contrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao aprovar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Cancelar contrato
    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cancelarContrato(@PathVariable Long id) {
        logger.info("Cancelando contrato ID: {}", id);
        try {
            ContratoCreditoResponse contrato = contratoCreditoService.cancelarContrato(id);
            return ResponseEntity.ok(contrato);
        } catch (RuntimeException e) {
            logger.error("Erro ao cancelar contrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao cancelar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Quitar contrato
    @PutMapping("/{id}/quitar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> quitarContrato(@PathVariable Long id) {
        logger.info("Quitando contrato ID: {}", id);
        try {
            ContratoCreditoResponse contrato = contratoCreditoService.quitarContrato(id);
            return ResponseEntity.ok(contrato);
        } catch (RuntimeException e) {
            logger.error("Erro ao quitar contrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao quitar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Desativar contrato
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desativarContrato(@PathVariable Long id) {
        logger.info("Desativando contrato ID: {}", id);
        try {
            contratoCreditoService.desativarContrato(id);
            return ResponseEntity.ok(Map.of("message", "Contrato desativado com sucesso"));
        } catch (RuntimeException e) {
            logger.error("Erro ao desativar contrato: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao desativar contrato: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }
}
