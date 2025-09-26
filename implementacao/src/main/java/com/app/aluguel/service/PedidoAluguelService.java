package com.app.aluguel.service;

import com.app.aluguel.model.*;
import com.app.aluguel.repository.PedidoAluguelRepository;
import com.app.aluguel.repository.ClienteRepository;
import com.app.aluguel.repository.AutomovelRepository;
import com.app.aluguel.repository.AgenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoAluguelService {
    
    @Autowired
    private PedidoAluguelRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    @Autowired
    private AgenteRepository agenteRepository;
    
    @Autowired
    private NotificacaoService notificacaoService;
    
    public List<PedidoAluguel> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    public List<PedidoAluguel> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }
    
    public List<PedidoAluguel> listarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return pedidoRepository.findByCliente(cliente);
    }
    
    public List<PedidoAluguel> listarPorAgente(Long agenteId) {
        Agente agente = agenteRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente não encontrado"));
        return pedidoRepository.findByAgenteAnalisador(agente);
    }
    
    public List<PedidoAluguel> listarPendentes() {
        return pedidoRepository.findByStatus(StatusPedido.PENDENTE);
    }
    
    public Optional<PedidoAluguel> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    public PedidoAluguel criarPedido(Long clienteId, Long automovelId, LocalDate dataInicio, LocalDate dataFim) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        Automovel automovel = automovelRepository.findById(automovelId)
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
        
        if (!automovel.isDisponivel()) {
            throw new RuntimeException("Automóvel não está disponível");
        }
        
        if (dataInicio.isBefore(LocalDate.now())) {
            throw new RuntimeException("Data de início deve ser futura");
        }
        
        if (dataFim.isBefore(dataInicio)) {
            throw new RuntimeException("Data de fim deve ser posterior à data de início");
        }
        
        // Calcular valor total baseado no período
        long dias = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim);
        Double valorTotal = automovel.getValorAluguel() * dias;
        
        PedidoAluguel pedido = new PedidoAluguel(cliente, automovel, dataInicio, dataFim, valorTotal);
        PedidoAluguel pedidoSalvo = pedidoRepository.save(pedido);
        
        // Enviar notificação
        notificacaoService.notificarPedidoCriado(pedidoSalvo);
        
        return pedidoSalvo;
    }
    
    public PedidoAluguel aprovarPedido(Long pedidoId, Long agenteId, String parecer) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Agente agente = agenteRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente não encontrado"));
        
        if (!pedido.isPendente()) {
            throw new RuntimeException("Pedido não está pendente");
        }
        
        pedido.aprovar(agente, parecer);
        PedidoAluguel pedidoSalvo = pedidoRepository.save(pedido);
        
        // Enviar notificação
        notificacaoService.notificarPedidoAprovado(pedidoSalvo);
        
        return pedidoSalvo;
    }
    
    public PedidoAluguel rejeitarPedido(Long pedidoId, Long agenteId, String parecer) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Agente agente = agenteRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente não encontrado"));
        
        if (!pedido.isPendente()) {
            throw new RuntimeException("Pedido não está pendente");
        }
        
        pedido.rejeitar(agente, parecer);
        PedidoAluguel pedidoSalvo = pedidoRepository.save(pedido);
        
        // Enviar notificação
        notificacaoService.notificarPedidoRejeitado(pedidoSalvo);
        
        return pedidoSalvo;
    }
    
    public PedidoAluguel cancelarPedido(Long pedidoId) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (!pedido.isPendente()) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser cancelados");
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }
    
    public List<PedidoAluguel> buscarPedidosPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return pedidoRepository.findByDataInicioBetween(dataInicio, dataFim);
    }
}
