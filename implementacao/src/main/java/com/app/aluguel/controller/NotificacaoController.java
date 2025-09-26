package com.app.aluguel.controller;

import com.app.aluguel.model.Notificacao;
import com.app.aluguel.model.TipoNotificacao;
import com.app.aluguel.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {
    
    @Autowired
    private NotificacaoService notificacaoService;
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacao> listarPorUsuario(@PathVariable Long usuarioId) {
        return notificacaoService.listarPorUsuario(usuarioId);
    }
    
    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public List<Notificacao> listarNaoLidasPorUsuario(@PathVariable Long usuarioId) {
        return notificacaoService.listarNaoLidasPorUsuario(usuarioId);
    }
    
    @GetMapping("/usuario/{usuarioId}/contar-nao-lidas")
    public ResponseEntity<Long> contarNaoLidasPorUsuario(@PathVariable Long usuarioId) {
        Long count = notificacaoService.contarNaoLidasPorUsuario(usuarioId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(@PathVariable Long id) {
        Optional<Notificacao> notificacao = notificacaoService.buscarPorId(id);
        return notificacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Notificacao> criarNotificacao(
            @RequestParam String titulo,
            @RequestParam String mensagem,
            @RequestParam TipoNotificacao tipo,
            @RequestParam Long usuarioId) {
        try {
            Notificacao notificacao = notificacaoService.criarNotificacao(titulo, mensagem, tipo, usuarioId);
            return ResponseEntity.ok(notificacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/marcar-lida")
    public ResponseEntity<Notificacao> marcarComoLida(@PathVariable Long id) {
        try {
            Notificacao notificacao = notificacaoService.marcarComoLida(id);
            return ResponseEntity.ok(notificacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/usuario/{usuarioId}/marcar-todas-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(@PathVariable Long usuarioId) {
        try {
            notificacaoService.marcarTodasComoLidas(usuarioId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarNotificacao(@PathVariable Long id) {
        try {
            notificacaoService.desativarNotificacao(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/sistema")
    public ResponseEntity<String> notificarSistema(
            @RequestParam String titulo,
            @RequestParam String mensagem,
            @RequestParam Long usuarioId) {
        try {
            notificacaoService.notificarSistema(titulo, mensagem, usuarioId);
            return ResponseEntity.ok("Notificação enviada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao enviar notificação: " + e.getMessage());
        }
    }
    
    @PostMapping("/sistema/todos")
    public ResponseEntity<String> notificarTodosUsuarios(
            @RequestParam String titulo,
            @RequestParam String mensagem) {
        try {
            notificacaoService.notificarTodosUsuarios(titulo, mensagem);
            return ResponseEntity.ok("Notificação enviada para todos os usuários");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
