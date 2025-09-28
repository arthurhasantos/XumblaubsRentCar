package com.xumblaubs.controller;

import com.xumblaubs.dto.EmpregadoraRequest;
import com.xumblaubs.dto.EmpregadoraResponse;
import com.xumblaubs.service.EmpregadoraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/empregadoras")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmpregadoraController {

    private static final Logger logger = LoggerFactory.getLogger(EmpregadoraController.class);

    @Autowired
    private EmpregadoraService empregadoraService;

    // Listar todas as empregadoras
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpregadoraResponse>> listarTodas() {
        logger.info("Listando todas as empregadoras");
        try {
            List<EmpregadoraResponse> empregadoras = empregadoraService.listarTodas();
            return ResponseEntity.ok(empregadoras);
        } catch (Exception e) {
            logger.error("Erro ao listar empregadoras: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar empregadoras por cliente
    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<EmpregadoraResponse>> listarPorCliente(@PathVariable Long clienteId) {
        logger.info("Listando empregadoras do cliente ID: {}", clienteId);
        try {
            List<EmpregadoraResponse> empregadoras = empregadoraService.listarPorCliente(clienteId);
            return ResponseEntity.ok(empregadoras);
        } catch (Exception e) {
            logger.error("Erro ao listar empregadoras do cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar empregadora por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando empregadora ID: {}", id);
        try {
            Optional<EmpregadoraResponse> empregadora = empregadoraService.buscarPorId(id);
            if (empregadora.isPresent()) {
                return ResponseEntity.ok(empregadora.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar empregadora: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Buscar empregadoras por nome
    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpregadoraResponse>> buscarPorNome(@RequestParam String nome) {
        logger.info("Buscando empregadoras por nome: {}", nome);
        try {
            List<EmpregadoraResponse> empregadoras = empregadoraService.buscarPorNome(nome);
            return ResponseEntity.ok(empregadoras);
        } catch (Exception e) {
            logger.error("Erro ao buscar empregadoras por nome: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Criar nova empregadora
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarEmpregadora(@Valid @RequestBody EmpregadoraRequest request) {
        logger.info("Criando nova empregadora: {}", request);
        try {
            EmpregadoraResponse empregadora = empregadoraService.criarEmpregadora(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(empregadora);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar empregadora: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao criar empregadora: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Atualizar empregadora
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarEmpregadora(@PathVariable Long id, @Valid @RequestBody EmpregadoraRequest request) {
        logger.info("Atualizando empregadora ID: {} com dados: {}", id, request);
        try {
            EmpregadoraResponse empregadora = empregadoraService.atualizarEmpregadora(id, request);
            return ResponseEntity.ok(empregadora);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar empregadora: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar empregadora: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Desativar empregadora
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desativarEmpregadora(@PathVariable Long id) {
        logger.info("Desativando empregadora ID: {}", id);
        try {
            empregadoraService.desativarEmpregadora(id);
            return ResponseEntity.ok(Map.of("message", "Empregadora desativada com sucesso"));
        } catch (RuntimeException e) {
            logger.error("Erro ao desativar empregadora: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao desativar empregadora: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Ativar empregadora
    @PutMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ativarEmpregadora(@PathVariable Long id) {
        logger.info("Ativando empregadora ID: {}", id);
        try {
            EmpregadoraResponse empregadora = empregadoraService.ativarEmpregadora(id);
            return ResponseEntity.ok(empregadora);
        } catch (RuntimeException e) {
            logger.error("Erro ao ativar empregadora: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao ativar empregadora: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Calcular rendimento total do cliente
    @GetMapping("/cliente/{clienteId}/rendimento-total")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> calcularRendimentoTotalCliente(@PathVariable Long clienteId) {
        logger.info("Calculando rendimento total do cliente ID: {}", clienteId);
        try {
            BigDecimal rendimentoTotal = empregadoraService.calcularRendimentoTotalCliente(clienteId);
            return ResponseEntity.ok(Map.of("rendimentoTotal", rendimentoTotal));
        } catch (Exception e) {
            logger.error("Erro ao calcular rendimento total: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Buscar empregadoras por rendimento
    @GetMapping("/rendimento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpregadoraResponse>> buscarPorRendimento(
            @RequestParam BigDecimal rendimentoMin, 
            @RequestParam BigDecimal rendimentoMax) {
        logger.info("Buscando empregadoras por rendimento entre {} e {}", rendimentoMin, rendimentoMax);
        try {
            List<EmpregadoraResponse> empregadoras = empregadoraService.buscarPorRendimento(rendimentoMin, rendimentoMax);
            return ResponseEntity.ok(empregadoras);
        } catch (Exception e) {
            logger.error("Erro ao buscar empregadoras por rendimento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar empregadoras por cargo
    @GetMapping("/cargo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpregadoraResponse>> buscarPorCargo(@RequestParam String cargo) {
        logger.info("Buscando empregadoras por cargo: {}", cargo);
        try {
            List<EmpregadoraResponse> empregadoras = empregadoraService.buscarPorCargo(cargo);
            return ResponseEntity.ok(empregadoras);
        } catch (Exception e) {
            logger.error("Erro ao buscar empregadoras por cargo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar empregadoras com maior rendimento
    @GetMapping("/maior-rendimento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpregadoraResponse>> buscarComMaiorRendimento() {
        logger.info("Buscando empregadoras com maior rendimento");
        try {
            List<EmpregadoraResponse> empregadoras = empregadoraService.buscarComMaiorRendimento();
            return ResponseEntity.ok(empregadoras);
        } catch (Exception e) {
            logger.error("Erro ao buscar empregadoras com maior rendimento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Contar empregadoras por cliente
    @GetMapping("/cliente/{clienteId}/count")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> contarPorCliente(@PathVariable Long clienteId) {
        logger.info("Contando empregadoras do cliente ID: {}", clienteId);
        try {
            Long count = empregadoraService.contarPorCliente(clienteId);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            logger.error("Erro ao contar empregadoras: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }
}
