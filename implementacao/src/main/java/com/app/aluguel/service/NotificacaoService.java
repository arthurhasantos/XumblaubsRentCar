package com.app.aluguel.service;

import com.app.aluguel.model.*;
import com.app.aluguel.repository.NotificacaoRepository;
import com.app.aluguel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacaoService {
    
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Notificacao> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return notificacaoRepository.findAtivasByUsuarioOrderByDataCriacaoDesc(usuario);
    }
    
    public List<Notificacao> listarNaoLidasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return notificacaoRepository.findNaoLidasByUsuarioOrderByDataCriacaoDesc(usuario);
    }
    
    public Long contarNaoLidasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return notificacaoRepository.countNaoLidasByUsuario(usuario);
    }
    
    public Optional<Notificacao> buscarPorId(Long id) {
        return notificacaoRepository.findById(id);
    }
    
    public Notificacao criarNotificacao(String titulo, String mensagem, TipoNotificacao tipo, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Notificacao notificacao = new Notificacao(titulo, mensagem, tipo, usuario);
        return notificacaoRepository.save(notificacao);
    }
    
    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        
        notificacao.marcarComoLida();
        return notificacaoRepository.save(notificacao);
    }
    
    public void marcarTodasComoLidas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        List<Notificacao> notificacoes = notificacaoRepository.findNaoLidasByUsuarioOrderByDataCriacaoDesc(usuario);
        for (Notificacao notificacao : notificacoes) {
            notificacao.marcarComoLida();
        }
        notificacaoRepository.saveAll(notificacoes);
    }
    
    public void desativarNotificacao(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        
        notificacao.setAtiva(false);
        notificacaoRepository.save(notificacao);
    }
    
    // Métodos específicos para diferentes tipos de notificação
    
    public void notificarPedidoCriado(PedidoAluguel pedido) {
        String titulo = "Novo Pedido de Aluguel";
        String mensagem = String.format("Um novo pedido de aluguel foi criado para o automóvel %s. " +
                "Valor total: R$ %.2f", 
                pedido.getAutomovel().getDescricaoCompleta(), 
                pedido.getValorTotal());
        
        // Notificar agentes
        List<Usuario> agentes = usuarioRepository.findByTipo(TipoUsuario.AGENTE);
        for (Usuario agente : agentes) {
            criarNotificacao(titulo, mensagem, TipoNotificacao.PEDIDO_CRIADO, agente.getId());
        }
    }
    
    public void notificarPedidoAprovado(PedidoAluguel pedido) {
        String titulo = "Pedido Aprovado";
        String mensagem = String.format("Seu pedido de aluguel para o automóvel %s foi aprovado! " +
                "Você pode prosseguir com a execução do contrato.", 
                pedido.getAutomovel().getDescricaoCompleta());
        
        criarNotificacao(titulo, mensagem, TipoNotificacao.PEDIDO_APROVADO, 
                pedido.getCliente().getUsuario().getId());
    }
    
    public void notificarPedidoRejeitado(PedidoAluguel pedido) {
        String titulo = "Pedido Rejeitado";
        String mensagem = String.format("Seu pedido de aluguel para o automóvel %s foi rejeitado. " +
                "Motivo: %s", 
                pedido.getAutomovel().getDescricaoCompleta(),
                pedido.getParecerFinanceiro());
        
        criarNotificacao(titulo, mensagem, TipoNotificacao.PEDIDO_REJEITADO, 
                pedido.getCliente().getUsuario().getId());
    }
    
    public void notificarContratoCriado(Contrato contrato) {
        String titulo = "Contrato Criado";
        String mensagem = String.format("Um novo contrato foi criado para o automóvel %s. " +
                "Período: %s a %s", 
                contrato.getAutomovel().getDescricaoCompleta(),
                contrato.getDataInicio(),
                contrato.getDataFim());
        
        criarNotificacao(titulo, mensagem, TipoNotificacao.CONTRATO_CRIADO, 
                contrato.getCliente().getUsuario().getId());
    }
    
    public void notificarPagamentoVencido(Contrato contrato) {
        String titulo = "Pagamento Vencido";
        String mensagem = String.format("O pagamento do contrato para o automóvel %s está vencido. " +
                "Entre em contato para regularizar a situação.", 
                contrato.getAutomovel().getDescricaoCompleta());
        
        criarNotificacao(titulo, mensagem, TipoNotificacao.PAGAMENTO_VENCIDO, 
                contrato.getCliente().getUsuario().getId());
    }
    
    public void notificarSistema(String titulo, String mensagem, Long usuarioId) {
        criarNotificacao(titulo, mensagem, TipoNotificacao.SISTEMA, usuarioId);
    }
    
    public void notificarTodosUsuarios(String titulo, String mensagem) {
        List<Usuario> usuarios = usuarioRepository.findByAtivoTrue();
        for (Usuario usuario : usuarios) {
            criarNotificacao(titulo, mensagem, TipoNotificacao.SISTEMA, usuario.getId());
        }
    }
}
