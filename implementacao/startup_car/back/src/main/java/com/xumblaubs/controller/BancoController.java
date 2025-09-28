package com.xumblaubs.controller;

import com.xumblaubs.dto.BancoRequest;
import com.xumblaubs.dto.BancoResponse;
import com.xumblaubs.service.BancoService;
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
@RequestMapping("/api/bancos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BancoController {

    private static final Logger logger = LoggerFactory.getLogger(BancoController.class);

    @Autowired
    private BancoService bancoService;

    // Listar todos os bancos
    @GetMapping
    public ResponseEntity<List<BancoResponse>> listarTodos() {
        logger.info("Listando todos os bancos");
        try {
            List<BancoResponse> bancos = bancoService.listarTodos();
            return ResponseEntity.ok(bancos);
        } catch (Exception e) {
            logger.error("Erro ao listar bancos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar banco por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando banco ID: {}", id);
        try {
            Optional<BancoResponse> banco = bancoService.buscarPorId(id);
            if (banco.isPresent()) {
                return ResponseEntity.ok(banco.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar banco: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Buscar banco por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        logger.info("Buscando banco por código: {}", codigo);
        try {
            Optional<BancoResponse> banco = bancoService.buscarPorCodigo(codigo);
            if (banco.isPresent()) {
                return ResponseEntity.ok(banco.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar banco por código: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Buscar bancos por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<BancoResponse>> buscarPorNome(@RequestParam String nome) {
        logger.info("Buscando bancos por nome: {}", nome);
        try {
            List<BancoResponse> bancos = bancoService.buscarPorNome(nome);
            return ResponseEntity.ok(bancos);
        } catch (Exception e) {
            logger.error("Erro ao buscar bancos por nome: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Criar novo banco
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> criarBanco(@Valid @RequestBody BancoRequest request) {
        logger.info("Criando novo banco: {}", request);
        try {
            BancoResponse banco = bancoService.criarBanco(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(banco);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar banco: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao criar banco: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Atualizar banco
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarBanco(@PathVariable Long id, @Valid @RequestBody BancoRequest request) {
        logger.info("Atualizando banco ID: {} com dados: {}", id, request);
        try {
            BancoResponse banco = bancoService.atualizarBanco(id, request);
            return ResponseEntity.ok(banco);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar banco: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar banco: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Desativar banco
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desativarBanco(@PathVariable Long id) {
        logger.info("Desativando banco ID: {}", id);
        try {
            bancoService.desativarBanco(id);
            return ResponseEntity.ok(Map.of("message", "Banco desativado com sucesso"));
        } catch (RuntimeException e) {
            logger.error("Erro ao desativar banco: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao desativar banco: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Ativar banco
    @PutMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ativarBanco(@PathVariable Long id) {
        logger.info("Ativando banco ID: {}", id);
        try {
            BancoResponse banco = bancoService.ativarBanco(id);
            return ResponseEntity.ok(banco);
        } catch (RuntimeException e) {
            logger.error("Erro ao ativar banco: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao ativar banco: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Buscar bancos com limite disponível
    @GetMapping("/limite-disponivel")
    public ResponseEntity<List<BancoResponse>> buscarComLimiteDisponivel() {
        logger.info("Buscando bancos com limite de crédito disponível");
        try {
            List<BancoResponse> bancos = bancoService.buscarBancosComLimiteDisponivel();
            return ResponseEntity.ok(bancos);
        } catch (Exception e) {
            logger.error("Erro ao buscar bancos com limite disponível: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar bancos por taxa de juros
    @GetMapping("/taxa-juros")
    public ResponseEntity<List<BancoResponse>> buscarPorTaxaJuros(
            @RequestParam Double taxaMin, 
            @RequestParam Double taxaMax) {
        logger.info("Buscando bancos por taxa de juros entre {} e {}", taxaMin, taxaMax);
        try {
            List<BancoResponse> bancos = bancoService.buscarPorTaxaJuros(taxaMin, taxaMax);
            return ResponseEntity.ok(bancos);
        } catch (Exception e) {
            logger.error("Erro ao buscar bancos por taxa de juros: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar bancos com mais contratos
    @GetMapping("/mais-contratos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BancoResponse>> buscarComMaisContratos() {
        logger.info("Buscando bancos com mais contratos");
        try {
            List<BancoResponse> bancos = bancoService.buscarBancosComMaisContratos();
            return ResponseEntity.ok(bancos);
        } catch (Exception e) {
            logger.error("Erro ao buscar bancos com mais contratos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
