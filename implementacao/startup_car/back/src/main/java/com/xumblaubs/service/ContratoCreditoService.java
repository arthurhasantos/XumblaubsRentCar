package com.xumblaubs.service;

import com.xumblaubs.dto.ContratoCreditoRequest;
import com.xumblaubs.dto.ContratoCreditoResponse;
import com.xumblaubs.entity.*;
import com.xumblaubs.repository.ContratoCreditoRepository;
import com.xumblaubs.repository.BancoRepository;
import com.xumblaubs.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContratoCreditoService {

    private static final Logger logger = LoggerFactory.getLogger(ContratoCreditoService.class);

    @Autowired
    private ContratoCreditoRepository contratoCreditoRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Listar todos os contratos
    @Transactional(readOnly = true)
    public List<ContratoCreditoResponse> listarTodos() {
        logger.info("Listando todos os contratos de crédito");
        List<ContratoCredito> contratos = contratoCreditoRepository.findByAtivoTrueOrderByCreatedAtDesc();
        return contratos.stream()
                .map(ContratoCreditoResponse::new)
                .collect(Collectors.toList());
    }

    // Listar contratos por cliente
    @Transactional(readOnly = true)
    public List<ContratoCreditoResponse> listarPorCliente(Long clienteId) {
        logger.info("Listando contratos do cliente ID: {}", clienteId);
        List<ContratoCredito> contratos = contratoCreditoRepository.findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(clienteId);
        return contratos.stream()
                .map(ContratoCreditoResponse::new)
                .collect(Collectors.toList());
    }

    // Listar contratos por banco
    @Transactional(readOnly = true)
    public List<ContratoCreditoResponse> listarPorBanco(Long bancoId) {
        logger.info("Listando contratos do banco ID: {}", bancoId);
        List<ContratoCredito> contratos = contratoCreditoRepository.findByBancoIdAndAtivoTrueOrderByCreatedAtDesc(bancoId);
        return contratos.stream()
                .map(ContratoCreditoResponse::new)
                .collect(Collectors.toList());
    }

    // Listar contratos pendentes
    @Transactional(readOnly = true)
    public List<ContratoCreditoResponse> listarPendentes() {
        logger.info("Listando contratos pendentes");
        List<ContratoCredito> contratos = contratoCreditoRepository.findContratosPendentes();
        return contratos.stream()
                .map(ContratoCreditoResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar contrato por ID
    @Transactional(readOnly = true)
    public Optional<ContratoCreditoResponse> buscarPorId(Long id) {
        logger.info("Buscando contrato de crédito ID: {}", id);
        return contratoCreditoRepository.findById(id)
                .filter(ContratoCredito::getAtivo)
                .map(ContratoCreditoResponse::new);
    }

    // Criar novo contrato
    public ContratoCreditoResponse criarContrato(ContratoCreditoRequest request) {
        logger.info("Criando novo contrato de crédito: {}", request);

        // Validar banco
        Banco banco = bancoRepository.findById(request.getBancoId())
                .orElseThrow(() -> new RuntimeException("Banco não encontrado com ID: " + request.getBancoId()));

        if (!banco.getAtivo()) {
            throw new RuntimeException("Banco está inativo");
        }

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        if (!cliente.getAtivo()) {
            throw new RuntimeException("Cliente está inativo");
        }

        // Verificar se número do contrato já existe
        if (contratoCreditoRepository.findByNumeroAndAtivoTrue(request.getNumero()).isPresent()) {
            throw new RuntimeException("Número do contrato já existe: " + request.getNumero());
        }

        // Verificar se cliente já possui contrato ativo
        if (contratoCreditoRepository.existsContratoAtivoDoCliente(request.getClienteId())) {
            throw new RuntimeException("Cliente já possui um contrato de crédito ativo");
        }

        // Limite de crédito removido - validação não aplicável

        // Criar contrato
        ContratoCredito contrato = new ContratoCredito();
        contrato.setNumero(request.getNumero());
        contrato.setValorTotal(request.getValorTotal());
        contrato.setPrazoMeses(request.getPrazoMeses());
        contrato.setBanco(banco);
        contrato.setCliente(cliente);
        contrato.setStatus(StatusContratoCredito.PENDENTE);
        contrato.setTaxaJuros(request.getTaxaJuros() != null ? request.getTaxaJuros() : new BigDecimal("0.03")); // Taxa padrão 3%
        contrato.setObservacoes(request.getObservacoes());
        contrato.setAtivo(true);

        // Calcular valor da parcela
        contrato.calcularValorParcela();

        // Salvar contrato
        contrato = contratoCreditoRepository.save(contrato);
        logger.info("Contrato de crédito criado com sucesso: {}", contrato.getId());

        return new ContratoCreditoResponse(contrato);
    }

    // Atualizar contrato
    public ContratoCreditoResponse atualizarContrato(Long id, ContratoCreditoRequest request) {
        logger.info("Atualizando contrato de crédito ID: {} com dados: {}", id, request);

        ContratoCredito contrato = contratoCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));

        if (!contrato.getAtivo()) {
            throw new RuntimeException("Contrato está inativo");
        }

        // Validar banco
        Banco banco = bancoRepository.findById(request.getBancoId())
                .orElseThrow(() -> new RuntimeException("Banco não encontrado com ID: " + request.getBancoId()));

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        // Verificar se número do contrato já existe (exceto para o próprio contrato)
        Optional<ContratoCredito> contratoExistente = contratoCreditoRepository.findByNumeroAndAtivoTrue(request.getNumero());
        if (contratoExistente.isPresent() && !contratoExistente.get().getId().equals(id)) {
            throw new RuntimeException("Número do contrato já existe: " + request.getNumero());
        }

        // Atualizar dados
        contrato.setNumero(request.getNumero());
        contrato.setValorTotal(request.getValorTotal());
        contrato.setPrazoMeses(request.getPrazoMeses());
        contrato.setBanco(banco);
        contrato.setCliente(cliente);
        contrato.setTaxaJuros(request.getTaxaJuros() != null ? request.getTaxaJuros() : new BigDecimal("0.03")); // Taxa padrão 3%
        contrato.setObservacoes(request.getObservacoes());

        // Recalcular valor da parcela
        contrato.calcularValorParcela();

        // Salvar alterações
        contrato = contratoCreditoRepository.save(contrato);
        logger.info("Contrato de crédito atualizado com sucesso: {}", contrato.getId());

        return new ContratoCreditoResponse(contrato);
    }

    // Aprovar contrato
    public ContratoCreditoResponse aprovarContrato(Long id) {
        logger.info("Aprovando contrato de crédito ID: {}", id);

        ContratoCredito contrato = contratoCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));

        if (!contrato.getAtivo()) {
            throw new RuntimeException("Contrato está inativo");
        }

        if (!StatusContratoCredito.PENDENTE.equals(contrato.getStatus())) {
            throw new RuntimeException("Apenas contratos pendentes podem ser aprovados");
        }

        contrato.setStatus(StatusContratoCredito.ATIVO);
        contrato.emitir();

        contrato = contratoCreditoRepository.save(contrato);
        logger.info("Contrato de crédito aprovado com sucesso: {}", contrato.getId());

        return new ContratoCreditoResponse(contrato);
    }

    // Cancelar contrato
    public ContratoCreditoResponse cancelarContrato(Long id) {
        logger.info("Cancelando contrato de crédito ID: {}", id);

        ContratoCredito contrato = contratoCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));

        if (!contrato.getAtivo()) {
            throw new RuntimeException("Contrato está inativo");
        }

        contrato.setStatus(StatusContratoCredito.CANCELADO);

        contrato = contratoCreditoRepository.save(contrato);
        logger.info("Contrato de crédito cancelado com sucesso: {}", contrato.getId());

        return new ContratoCreditoResponse(contrato);
    }

    // Quitar contrato
    public ContratoCreditoResponse quitarContrato(Long id) {
        logger.info("Quitando contrato de crédito ID: {}", id);

        ContratoCredito contrato = contratoCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));

        if (!contrato.getAtivo()) {
            throw new RuntimeException("Contrato está inativo");
        }

        if (!StatusContratoCredito.ATIVO.equals(contrato.getStatus())) {
            throw new RuntimeException("Apenas contratos ativos podem ser quitados");
        }

        contrato.setStatus(StatusContratoCredito.QUITADO);

        contrato = contratoCreditoRepository.save(contrato);
        logger.info("Contrato de crédito quitado com sucesso: {}", contrato.getId());

        return new ContratoCreditoResponse(contrato);
    }

    // Desativar contrato (soft delete)
    public void desativarContrato(Long id) {
        logger.info("Desativando contrato de crédito ID: {}", id);

        ContratoCredito contrato = contratoCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));

        contrato.setAtivo(false);
        contratoCreditoRepository.save(contrato);
        logger.info("Contrato de crédito desativado com sucesso: {}", contrato.getId());
    }
}
