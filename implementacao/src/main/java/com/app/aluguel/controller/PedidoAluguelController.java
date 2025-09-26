package com.app.aluguel.controller;

import com.app.aluguel.model.PedidoAluguel;
import com.app.aluguel.model.StatusPedido;
import com.app.aluguel.service.PedidoAluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoAluguelController {
    
    @Autowired
    private PedidoAluguelService pedidoService;
    
    @GetMapping
    public List<PedidoAluguel> listarTodos() {
        return pedidoService.listarTodos();
    }
    
    @GetMapping("/status/{status}")
    public List<PedidoAluguel> listarPorStatus(@PathVariable StatusPedido status) {
        return pedidoService.listarPorStatus(status);
    }
    
    @GetMapping("/pendentes")
    public List<PedidoAluguel> listarPendentes() {
        return pedidoService.listarPendentes();
    }
    
    @GetMapping("/cliente/{clienteId}")
    public List<PedidoAluguel> listarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPorCliente(clienteId);
    }
    
    @GetMapping("/agente/{agenteId}")
    public List<PedidoAluguel> listarPorAgente(@PathVariable Long agenteId) {
        return pedidoService.listarPorAgente(agenteId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoAluguel> buscarPorId(@PathVariable Long id) {
        Optional<PedidoAluguel> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PedidoAluguel> criarPedido(
            @RequestParam Long clienteId,
            @RequestParam Long automovelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        try {
            PedidoAluguel pedido = pedidoService.criarPedido(clienteId, automovelId, dataInicio, dataFim);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/aprovar")
    public ResponseEntity<PedidoAluguel> aprovarPedido(
            @PathVariable Long id,
            @RequestParam Long agenteId,
            @RequestParam String parecer) {
        try {
            PedidoAluguel pedido = pedidoService.aprovarPedido(id, agenteId, parecer);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/rejeitar")
    public ResponseEntity<PedidoAluguel> rejeitarPedido(
            @PathVariable Long id,
            @RequestParam Long agenteId,
            @RequestParam String parecer) {
        try {
            PedidoAluguel pedido = pedidoService.rejeitarPedido(id, agenteId, parecer);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PedidoAluguel> cancelarPedido(@PathVariable Long id) {
        try {
            PedidoAluguel pedido = pedidoService.cancelarPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/periodo")
    public List<PedidoAluguel> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return pedidoService.buscarPedidosPorPeriodo(dataInicio, dataFim);
    }
}
