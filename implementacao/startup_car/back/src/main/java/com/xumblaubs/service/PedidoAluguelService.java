package com.xumblaubs.service;

import com.xumblaubs.dto.PedidoAluguelRequest;
import com.xumblaubs.dto.PedidoAluguelResponse;
import com.xumblaubs.entity.*;
import com.xumblaubs.repository.PedidoAluguelRepository;
import com.xumblaubs.repository.ClienteRepository;
import com.xumblaubs.repository.AutomovelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoAluguelService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoAluguelService.class);

    @Autowired
    private PedidoAluguelRepository pedidoAluguelRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AutomovelRepository automovelRepository;

    // Listar todos os pedidos
    @Transactional(readOnly = true)
    public List<PedidoAluguelResponse> listarTodos() {
        logger.info("Listando todos os pedidos de aluguel");
        List<PedidoAluguel> pedidos = pedidoAluguelRepository.findByAtivoTrueOrderByCreatedAtDesc();
        return pedidos.stream()
                .map(PedidoAluguelResponse::new)
                .collect(Collectors.toList());
    }

    // Listar pedidos por cliente
    @Transactional(readOnly = true)
    public List<PedidoAluguelResponse> listarPorCliente(Long clienteId) {
        logger.info("Listando pedidos do cliente ID: {}", clienteId);
        List<PedidoAluguel> pedidos = pedidoAluguelRepository.findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(clienteId);
        return pedidos.stream()
                .map(PedidoAluguelResponse::new)
                .collect(Collectors.toList());
    }

    // Listar pedidos pendentes (para administradores)
    @Transactional(readOnly = true)
    public List<PedidoAluguelResponse> listarPendentes() {
        logger.info("Listando pedidos pendentes");
        List<PedidoAluguel> pedidos = pedidoAluguelRepository.findPedidosPendentes();
        return pedidos.stream()
                .map(PedidoAluguelResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar pedido por ID
    @Transactional(readOnly = true)
    public Optional<PedidoAluguelResponse> buscarPorId(Long id) {
        logger.info("Buscando pedido por ID: {}", id);
        return pedidoAluguelRepository.findById(id)
                .filter(PedidoAluguel::getAtivo)
                .map(PedidoAluguelResponse::new);
    }

    // Criar novo pedido
    public PedidoAluguelResponse criarPedido(PedidoAluguelRequest request) {
        logger.info("Criando novo pedido de aluguel: {}", request);

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        if (!cliente.getAtivo()) {
            throw new RuntimeException("Cliente está inativo");
        }

        // Validar automóvel
        Automovel automovel = automovelRepository.findById(request.getAutomovelId())
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com ID: " + request.getAutomovelId()));

        if (!automovel.getAtivo()) {
            throw new RuntimeException("Automóvel está inativo");
        }

        // Validar datas
        if (!request.isDataFimValida()) {
            throw new RuntimeException("Data de fim deve ser posterior à data de início");
        }

        if (request.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Data de início deve ser no futuro");
        }

        // Verificar se automóvel está disponível no período
        if (pedidoAluguelRepository.existsPedidoAtivoNoPeriodo(request.getAutomovelId(), request.getDataInicio(), request.getDataFim())) {
            throw new RuntimeException("Automóvel não está disponível no período solicitado");
        }

        // Verificar se cliente já tem pedido ativo
        if (pedidoAluguelRepository.existsPedidoAtivoDoCliente(request.getClienteId())) {
            throw new RuntimeException("Cliente já possui um pedido ativo");
        }

        // Criar pedido
        PedidoAluguel pedido = new PedidoAluguel();
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);
        pedido.setDataInicio(request.getDataInicio());
        pedido.setDataFim(request.getDataFim());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setObservacoes(request.getObservacoes());
        pedido.setAtivo(true);

        // Calcular valor total (exemplo: R$ 100 por dia)
        long diasAluguel = request.getDiasAluguel();
        pedido.setValorTotal(diasAluguel * 100.0);

        PedidoAluguel pedidoSalvo = pedidoAluguelRepository.save(pedido);
        logger.info("Pedido criado com sucesso. ID: {}", pedidoSalvo.getId());

        return new PedidoAluguelResponse(pedidoSalvo);
    }

    // Atualizar pedido
    @Transactional
    public PedidoAluguelResponse atualizarPedido(Long id, PedidoAluguelRequest request) {
        logger.info("Atualizando pedido ID: {} com dados: {}", id, request);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido está inativo");
        }

        // Apenas pedidos pendentes podem ser editados
        if (!pedido.isPendente()) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser editados");
        }

        // Buscar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        // Buscar automóvel
        Automovel automovel = automovelRepository.findById(request.getAutomovelId())
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com ID: " + request.getAutomovelId()));

        // Verificar se automóvel está disponível no período (excluindo o próprio pedido)
        if (pedidoAluguelRepository.existsPedidoAtivoNoPeriodoExcluindoPedido(
                request.getAutomovelId(), request.getDataInicio(), request.getDataFim(), id)) {
            throw new RuntimeException("Automóvel não está disponível no período solicitado");
        }

        // Atualizar dados do pedido
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);
        pedido.setDataInicio(request.getDataInicio());
        pedido.setDataFim(request.getDataFim());
        pedido.setObservacoes(request.getObservacoes());

        // Recalcular valor total
        long diasAluguel = request.getDiasAluguel();
        pedido.setValorTotal(diasAluguel * 100.0);

        PedidoAluguel pedidoAtualizado = pedidoAluguelRepository.save(pedido);
        logger.info("Pedido atualizado com sucesso. ID: {}", id);

        return new PedidoAluguelResponse(pedidoAtualizado);
    }

    // Aprovar pedido (apenas administradores)
    public PedidoAluguelResponse aprovarPedido(Long id) {
        logger.info("Aprovando pedido ID: {}", id);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido está inativo");
        }

        if (!pedido.isPendente()) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser aprovados");
        }

        // Verificar se automóvel ainda está disponível
        if (pedidoAluguelRepository.existsPedidoAtivoNoPeriodo(pedido.getAutomovel().getId(), pedido.getDataInicio(), pedido.getDataFim())) {
            throw new RuntimeException("Automóvel não está mais disponível no período solicitado");
        }

        pedido.aprovar();
        PedidoAluguel pedidoAtualizado = pedidoAluguelRepository.save(pedido);
        logger.info("Pedido aprovado com sucesso. ID: {}", id);

        return new PedidoAluguelResponse(pedidoAtualizado);
    }

    // Rejeitar pedido (apenas administradores)
    public PedidoAluguelResponse rejeitarPedido(Long id, String motivo) {
        logger.info("Rejeitando pedido ID: {} com motivo: {}", id, motivo);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido está inativo");
        }

        if (!pedido.isPendente()) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser rejeitados");
        }

        if (motivo == null || motivo.trim().isEmpty()) {
            throw new RuntimeException("Motivo da rejeição é obrigatório");
        }

        pedido.rejeitar(motivo.trim());
        PedidoAluguel pedidoAtualizado = pedidoAluguelRepository.save(pedido);
        logger.info("Pedido rejeitado com sucesso. ID: {}", id);

        return new PedidoAluguelResponse(pedidoAtualizado);
    }

    // Iniciar aluguel (apenas administradores)
    public PedidoAluguelResponse iniciarAluguel(Long id) {
        logger.info("Iniciando aluguel do pedido ID: {}", id);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido está inativo");
        }

        if (!pedido.isAprovado()) {
            throw new RuntimeException("Apenas pedidos aprovados podem ser iniciados");
        }

        pedido.iniciar();
        PedidoAluguel pedidoAtualizado = pedidoAluguelRepository.save(pedido);
        logger.info("Aluguel iniciado com sucesso. ID: {}", id);

        return new PedidoAluguelResponse(pedidoAtualizado);
    }

    // Finalizar aluguel (apenas administradores)
    public PedidoAluguelResponse finalizarAluguel(Long id) {
        logger.info("Finalizando aluguel do pedido ID: {}", id);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido está inativo");
        }

        if (!pedido.isEmAndamento()) {
            throw new RuntimeException("Apenas aluguéis em andamento podem ser finalizados");
        }

        pedido.finalizar();
        PedidoAluguel pedidoAtualizado = pedidoAluguelRepository.save(pedido);
        logger.info("Aluguel finalizado com sucesso. ID: {}", id);

        return new PedidoAluguelResponse(pedidoAtualizado);
    }

    // Desativar pedido (soft delete)
    public void desativarPedido(Long id) {
        logger.info("Desativando pedido ID: {}", id);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido já está inativo");
        }

        pedido.setAtivo(false);
        pedidoAluguelRepository.save(pedido);
        logger.info("Pedido desativado com sucesso. ID: {}", id);
    }

    // Atualizar status do pedido
    public PedidoAluguelResponse atualizarStatus(Long id, String statusStr) {
        logger.info("Atualizando status do pedido ID: {} para {}", id, statusStr);

        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getAtivo()) {
            throw new RuntimeException("Pedido está inativo");
        }

        try {
            StatusPedido novoStatus = StatusPedido.valueOf(statusStr.toUpperCase());
            pedido.setStatus(novoStatus);
            PedidoAluguel pedidoAtualizado = pedidoAluguelRepository.save(pedido);
            logger.info("Status do pedido atualizado com sucesso. ID: {}, Status: {}", id, novoStatus);
            return new PedidoAluguelResponse(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + statusStr);
        }
    }

    // Buscar pedidos com filtros
    @Transactional(readOnly = true)
    public List<PedidoAluguelResponse> buscarComFiltros(String search, Long clienteId, Long automovelId, StatusPedido status, 
                                                        LocalDateTime dataInicio, LocalDateTime dataFim, Boolean ativo) {
        logger.info("Buscando pedidos com filtros - Search: {}, Cliente: {}, Automóvel: {}, Status: {}, Ativo: {}", 
                   search, clienteId, automovelId, status, ativo);

        List<PedidoAluguel> pedidos = pedidoAluguelRepository.findByFiltros(clienteId, automovelId, status, dataInicio, dataFim, ativo);
        
        // Se há um termo de busca, filtrar por nome do cliente ou placa do automóvel
        if (search != null && !search.trim().isEmpty()) {
            String searchTerm = search.toLowerCase().trim();
            pedidos = pedidos.stream()
                    .filter(pedido -> 
                        pedido.getCliente().getNome().toLowerCase().contains(searchTerm) ||
                        pedido.getAutomovel().getPlaca().toLowerCase().contains(searchTerm) ||
                        pedido.getAutomovel().getModelo().toLowerCase().contains(searchTerm)
                    )
                    .collect(Collectors.toList());
        }
        
        return pedidos.stream()
                .map(PedidoAluguelResponse::new)
                .collect(Collectors.toList());
    }

    // Estatísticas
    @Transactional(readOnly = true)
    public long contarPedidosPorStatus(StatusPedido status) {
        return pedidoAluguelRepository.findByStatusAndAtivoTrueOrderByCreatedAtDesc(status).size();
    }

    @Transactional(readOnly = true)
    public long contarPedidosPendentes() {
        return pedidoAluguelRepository.findPedidosPendentes().size();
    }

    @Transactional(readOnly = true)
    public long contarPedidosAtivos() {
        return pedidoAluguelRepository.findByAtivoTrueOrderByCreatedAtDesc().size();
    }

    // Finalizar pedidos automaticamente (para ser chamado por um job)
    public void finalizarPedidosExpirados() {
        logger.info("Finalizando pedidos expirados");
        LocalDateTime agora = LocalDateTime.now();
        List<PedidoAluguel> pedidosParaFinalizar = pedidoAluguelRepository.findPedidosParaFinalizar(agora);
        
        for (PedidoAluguel pedido : pedidosParaFinalizar) {
            pedido.finalizar();
            pedidoAluguelRepository.save(pedido);
            logger.info("Pedido finalizado automaticamente. ID: {}", pedido.getId());
        }
    }
}
