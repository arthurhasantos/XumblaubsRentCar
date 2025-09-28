package com.xumblaubs.controller;

import com.xumblaubs.dto.PedidoAluguelRequest;
import com.xumblaubs.dto.PedidoAluguelResponse;
import com.xumblaubs.entity.StatusPedido;
import com.xumblaubs.service.PedidoAluguelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PedidoAluguelController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoAluguelController.class);

    @Autowired
    private PedidoAluguelService pedidoAluguelService;

    // Listar todos os pedidos (público - usuários autenticados)
    @GetMapping
    public ResponseEntity<List<PedidoAluguelResponse>> listarTodos() {
        logger.info("Listando todos os pedidos de aluguel");
        try {
            List<PedidoAluguelResponse> pedidos = pedidoAluguelService.listarTodos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            logger.error("Erro ao listar pedidos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar pedidos por cliente (público - usuários autenticados)
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoAluguelResponse>> listarPorCliente(@PathVariable Long clienteId) {
        logger.info("Listando pedidos do cliente ID: {}", clienteId);
        try {
            List<PedidoAluguelResponse> pedidos = pedidoAluguelService.listarPorCliente(clienteId);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            logger.error("Erro ao listar pedidos do cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar pedidos pendentes (apenas administradores)
    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PedidoAluguelResponse>> listarPendentes() {
        logger.info("Listando pedidos pendentes");
        try {
            List<PedidoAluguelResponse> pedidos = pedidoAluguelService.listarPendentes();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            logger.error("Erro ao listar pedidos pendentes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar pedido por ID (público - usuários autenticados)
    @GetMapping("/{id}")
    public ResponseEntity<PedidoAluguelResponse> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando pedido por ID: {}", id);
        try {
            Optional<PedidoAluguelResponse> pedido = pedidoAluguelService.buscarPorId(id);
            return pedido.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao buscar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Criar novo pedido (público - usuários autenticados)
    @PostMapping
    public ResponseEntity<?> criarPedido(@Valid @RequestBody PedidoAluguelRequest request) {
        logger.info("Criando novo pedido de aluguel: {}", request);
        try {
            PedidoAluguelResponse pedido = pedidoAluguelService.criarPedido(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao criar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Atualizar pedido (público - usuários autenticados)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable Long id, @Valid @RequestBody PedidoAluguelRequest request) {
        logger.info("Atualizando pedido de aluguel ID: {} com dados: {}", id, request);
        try {
            PedidoAluguelResponse pedido = pedidoAluguelService.atualizarPedido(id, request);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Aprovar pedido (apenas administradores)
    @PutMapping("/{id}/aprovar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> aprovarPedido(@PathVariable Long id) {
        logger.info("Aprovando pedido ID: {}", id);
        try {
            PedidoAluguelResponse pedido = pedidoAluguelService.aprovarPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao aprovar pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao aprovar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Rejeitar pedido (apenas administradores)
    @PutMapping("/{id}/rejeitar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejeitarPedido(@PathVariable Long id, @RequestBody Map<String, String> request) {
        logger.info("Rejeitando pedido ID: {}", id);
        try {
            String motivo = request.get("motivo");
            PedidoAluguelResponse pedido = pedidoAluguelService.rejeitarPedido(id, motivo);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao rejeitar pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao rejeitar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Iniciar aluguel (apenas administradores)
    @PutMapping("/{id}/iniciar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> iniciarAluguel(@PathVariable Long id) {
        logger.info("Iniciando aluguel do pedido ID: {}", id);
        try {
            PedidoAluguelResponse pedido = pedidoAluguelService.iniciarAluguel(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao iniciar aluguel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao iniciar aluguel: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Finalizar aluguel (apenas administradores)
    @PutMapping("/{id}/finalizar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> finalizarAluguel(@PathVariable Long id) {
        logger.info("Finalizando aluguel do pedido ID: {}", id);
        try {
            PedidoAluguelResponse pedido = pedidoAluguelService.finalizarAluguel(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao finalizar aluguel: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao finalizar aluguel: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Desativar pedido (apenas administradores)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desativarPedido(@PathVariable Long id) {
        logger.info("Desativando pedido ID: {}", id);
        try {
            pedidoAluguelService.desativarPedido(id);
            return ResponseEntity.ok(Map.of("message", "Pedido desativado com sucesso"));
        } catch (RuntimeException e) {
            logger.error("Erro ao desativar pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao desativar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Atualizar status do pedido (apenas administradores)
    @PatchMapping("/{id}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @PathVariable String status) {
        logger.info("Atualizando status do pedido ID: {} para {}", id, status);
        try {
            PedidoAluguelResponse pedido = pedidoAluguelService.atualizarStatus(id, status);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar status: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }

    // Buscar pedidos com filtros (público - usuários autenticados)
    @GetMapping("/filtros")
    public ResponseEntity<List<PedidoAluguelResponse>> buscarComFiltros(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long automovelId,
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam(required = false) Boolean ativo) {
        
        logger.info("Buscando pedidos com filtros - Search: {}, Cliente: {}, Automóvel: {}, Status: {}, Ativo: {}", 
                   search, clienteId, automovelId, status, ativo);

        try {
            LocalDateTime dataInicioParsed = null;
            LocalDateTime dataFimParsed = null;

            if (dataInicio != null && !dataInicio.isEmpty()) {
                dataInicioParsed = LocalDateTime.parse(dataInicio);
            }
            if (dataFim != null && !dataFim.isEmpty()) {
                dataFimParsed = LocalDateTime.parse(dataFim);
            }

            List<PedidoAluguelResponse> pedidos = pedidoAluguelService.buscarComFiltros(
                search, clienteId, automovelId, status, dataInicioParsed, dataFimParsed, ativo);
            
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            logger.error("Erro ao buscar pedidos com filtros: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Estatísticas (público)
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        logger.info("Obtendo estatísticas dos pedidos");
        try {
            Map<String, Object> estatisticas = Map.of(
                "totalPedidos", pedidoAluguelService.contarPedidosAtivos(),
                "pedidosPendentes", pedidoAluguelService.contarPedidosPendentes(),
                "pedidosAprovados", pedidoAluguelService.contarPedidosPorStatus(StatusPedido.APROVADO),
                "pedidosRejeitados", pedidoAluguelService.contarPedidosPorStatus(StatusPedido.REJEITADO),
                "pedidosEmAndamento", pedidoAluguelService.contarPedidosPorStatus(StatusPedido.EM_ANDAMENTO),
                "pedidosFinalizados", pedidoAluguelService.contarPedidosPorStatus(StatusPedido.FINALIZADO)
            );
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            logger.error("Erro ao obter estatísticas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Finalizar pedidos expirados (apenas administradores)
    @PostMapping("/finalizar-expirados")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> finalizarPedidosExpirados() {
        logger.info("Finalizando pedidos expirados");
        try {
            pedidoAluguelService.finalizarPedidosExpirados();
            return ResponseEntity.ok(Map.of("message", "Pedidos expirados finalizados com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao finalizar pedidos expirados: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno do servidor"));
        }
    }
}
