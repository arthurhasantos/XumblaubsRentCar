package com.app.aluguel.controller;

import com.app.aluguel.service.AvaliacaoFinanceiraService;
import com.app.aluguel.service.PedidoAluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoFinanceiraController {
    
    @Autowired
    private AvaliacaoFinanceiraService avaliacaoService;
    
    @Autowired
    private PedidoAluguelService pedidoService;
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Map<String, Object>> analisarPedido(@PathVariable Long pedidoId) {
        try {
            Map<String, Object> resultado = avaliacaoService.analisarPedido(pedidoId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/cliente/{clienteId}/capacidade")
    public ResponseEntity<Map<String, Object>> calcularCapacidadePagamento(@PathVariable Long clienteId) {
        try {
            Map<String, Object> resultado = avaliacaoService.calcularCapacidadePagamento(clienteId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/cliente/{clienteId}/verificar")
    public ResponseEntity<Boolean> verificarCapacidadePagamento(
            @PathVariable Long clienteId, 
            @RequestParam double valorAluguel) {
        try {
            boolean podePagar = avaliacaoService.verificarCapacidadePagamento(clienteId, valorAluguel);
            return ResponseEntity.ok(podePagar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/pedido/{pedidoId}/aprovar")
    public ResponseEntity<String> aprovarComAnalise(
            @PathVariable Long pedidoId,
            @RequestParam Long agenteId,
            @RequestParam(required = false) String observacoesAdicionais) {
        try {
            // Realizar análise financeira
            Map<String, Object> analise = avaliacaoService.analisarPedido(pedidoId);
            
            // Verificar se deve ser aprovado automaticamente
            boolean aprovado = (Boolean) analise.get("aprovado");
            String parecer = (String) analise.get("parecer");
            
            if (observacoesAdicionais != null && !observacoesAdicionais.trim().isEmpty()) {
                parecer += "\n\nOBSERVAÇÕES ADICIONAIS:\n" + observacoesAdicionais;
            }
            
            if (aprovado) {
                pedidoService.aprovarPedido(pedidoId, agenteId, parecer);
                return ResponseEntity.ok("Pedido aprovado com base na análise financeira");
            } else {
                return ResponseEntity.badRequest().body("Pedido não atende aos critérios de aprovação automática");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao processar análise: " + e.getMessage());
        }
    }
    
    @PostMapping("/pedido/{pedidoId}/rejeitar")
    public ResponseEntity<String> rejeitarComAnalise(
            @PathVariable Long pedidoId,
            @RequestParam Long agenteId,
            @RequestParam(required = false) String motivoRejeicao) {
        try {
            // Realizar análise financeira
            Map<String, Object> analise = avaliacaoService.analisarPedido(pedidoId);
            String parecer = (String) analise.get("parecer");
            
            if (motivoRejeicao != null && !motivoRejeicao.trim().isEmpty()) {
                parecer += "\n\nMOTIVO DA REJEIÇÃO:\n" + motivoRejeicao;
            }
            
            pedidoService.rejeitarPedido(pedidoId, agenteId, parecer);
            return ResponseEntity.ok("Pedido rejeitado com base na análise financeira");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao processar análise: " + e.getMessage());
        }
    }
}
