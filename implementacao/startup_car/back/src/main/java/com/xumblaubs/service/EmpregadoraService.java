package com.xumblaubs.service;

import com.xumblaubs.dto.EmpregadoraRequest;
import com.xumblaubs.dto.EmpregadoraResponse;
import com.xumblaubs.entity.Empregadora;
import com.xumblaubs.entity.Cliente;
import com.xumblaubs.entity.User;
import com.xumblaubs.entity.Role;
import com.xumblaubs.repository.EmpregadoraRepository;
import com.xumblaubs.repository.ClienteRepository;
import com.xumblaubs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpregadoraService {

    private static final Logger logger = LoggerFactory.getLogger(EmpregadoraService.class);

    @Autowired
    private EmpregadoraRepository empregadoraRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar todas as empregadoras
    @Transactional(readOnly = true)
    public List<EmpregadoraResponse> listarTodas() {
        logger.info("Listando todas as empregadoras");
        List<Empregadora> empregadoras = empregadoraRepository.findByAtivoTrueOrderByNomeAsc();
        return empregadoras.stream()
                .map(EmpregadoraResponse::new)
                .collect(Collectors.toList());
    }

    // Listar empregadoras por cliente
    @Transactional(readOnly = true)
    public List<EmpregadoraResponse> listarPorCliente(Long clienteId) {
        logger.info("Listando empregadoras do cliente ID: {}", clienteId);
        List<Empregadora> empregadoras = empregadoraRepository.findByClienteIdAndAtivoTrueOrderByNomeAsc(clienteId);
        return empregadoras.stream()
                .map(EmpregadoraResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar empregadora por ID
    @Transactional(readOnly = true)
    public Optional<EmpregadoraResponse> buscarPorId(Long id) {
        logger.info("Buscando empregadora ID: {}", id);
        return empregadoraRepository.findById(id)
                .filter(Empregadora::getAtivo)
                .map(EmpregadoraResponse::new);
    }

    // Buscar empregadoras por nome
    @Transactional(readOnly = true)
    public List<EmpregadoraResponse> buscarPorNome(String nome) {
        logger.info("Buscando empregadoras por nome: {}", nome);
        List<Empregadora> empregadoras = empregadoraRepository.findByNomeContainingIgnoreCaseAndAtivoTrueOrderByNomeAsc(nome);
        return empregadoras.stream()
                .map(EmpregadoraResponse::new)
                .collect(Collectors.toList());
    }

    // Criar nova empregadora
    public EmpregadoraResponse criarEmpregadora(EmpregadoraRequest request) {
        logger.info("Criando nova empregadora: {}", request);

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        if (!cliente.getAtivo()) {
            throw new RuntimeException("Cliente está inativo");
        }

        // Verificar se cliente já possui 3 empregadoras ativas
        if (empregadoraRepository.clientePossuiMaximoEmpregadoras(request.getClienteId())) {
            throw new RuntimeException("Cliente já possui o máximo de 3 empregadoras ativas");
        }

        // Criar empregadora
        Empregadora empregadora = new Empregadora();
        empregadora.setNome(request.getNome());
        empregadora.setRendimento(request.getRendimento());
        empregadora.setCliente(cliente);
        empregadora.setEndereco(request.getEndereco());
        empregadora.setTelefone(request.getTelefone());
        empregadora.setEmail(request.getEmail());
        empregadora.setSenha(passwordEncoder.encode(request.getSenha()));
        empregadora.setCnpj(request.getCnpj());
        empregadora.setCargo(request.getCargo());
        empregadora.setDataAdmissao(request.getDataAdmissao());
        empregadora.setAtivo(true);

        // Salvar empregadora
        empregadora = empregadoraRepository.save(empregadora);
        logger.info("Empregadora criada com sucesso: {}", empregadora.getId());

        // Criar usuário para a empregadora
        User user = new User();
        user.setName(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getSenha()));
        user.setRole(Role.USER); // Role de usuário para empregadoras
        user.setEnabled(true);
        userRepository.save(user);
        logger.info("Usuário criado para a empregadora: {}", user.getEmail());

        return new EmpregadoraResponse(empregadora);
    }

    // Atualizar empregadora
    public EmpregadoraResponse atualizarEmpregadora(Long id, EmpregadoraRequest request) {
        logger.info("Atualizando empregadora ID: {} com dados: {}", id, request);

        Empregadora empregadora = empregadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empregadora não encontrada com ID: " + id));

        if (!empregadora.getAtivo()) {
            throw new RuntimeException("Empregadora está inativa");
        }

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));

        // Atualizar dados
        empregadora.setNome(request.getNome());
        empregadora.setRendimento(request.getRendimento());
        empregadora.setCliente(cliente);
        empregadora.setEndereco(request.getEndereco());
        empregadora.setTelefone(request.getTelefone());
        empregadora.setEmail(request.getEmail());
        empregadora.setSenha(passwordEncoder.encode(request.getSenha()));
        empregadora.setCnpj(request.getCnpj());
        empregadora.setCargo(request.getCargo());
        empregadora.setDataAdmissao(request.getDataAdmissao());

        // Salvar alterações
        empregadora = empregadoraRepository.save(empregadora);
        logger.info("Empregadora atualizada com sucesso: {}", empregadora.getId());

        return new EmpregadoraResponse(empregadora);
    }

    // Desativar empregadora (soft delete)
    public void desativarEmpregadora(Long id) {
        logger.info("Desativando empregadora ID: {}", id);

        Empregadora empregadora = empregadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empregadora não encontrada com ID: " + id));

        empregadora.setAtivo(false);
        empregadoraRepository.save(empregadora);
        logger.info("Empregadora desativada com sucesso: {}", empregadora.getId());
    }

    // Ativar empregadora
    public EmpregadoraResponse ativarEmpregadora(Long id) {
        logger.info("Ativando empregadora ID: {}", id);

        Empregadora empregadora = empregadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empregadora não encontrada com ID: " + id));

        // Verificar se cliente já possui 3 empregadoras ativas
        if (empregadoraRepository.clientePossuiMaximoEmpregadoras(empregadora.getCliente().getId())) {
            throw new RuntimeException("Cliente já possui o máximo de 3 empregadoras ativas");
        }

        empregadora.setAtivo(true);
        empregadora = empregadoraRepository.save(empregadora);
        logger.info("Empregadora ativada com sucesso: {}", empregadora.getId());

        return new EmpregadoraResponse(empregadora);
    }

    // Calcular rendimento total do cliente
    @Transactional(readOnly = true)
    public BigDecimal calcularRendimentoTotalCliente(Long clienteId) {
        logger.info("Calculando rendimento total do cliente ID: {}", clienteId);
        return empregadoraRepository.calcularRendimentoTotalCliente(clienteId);
    }

    // Buscar empregadoras por rendimento
    @Transactional(readOnly = true)
    public List<EmpregadoraResponse> buscarPorRendimento(BigDecimal rendimentoMin, BigDecimal rendimentoMax) {
        logger.info("Buscando empregadoras por rendimento entre {} e {}", rendimentoMin, rendimentoMax);
        List<Empregadora> empregadoras = empregadoraRepository.findByRendimentoBetween(rendimentoMin, rendimentoMax);
        return empregadoras.stream()
                .map(EmpregadoraResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar empregadoras por cargo
    @Transactional(readOnly = true)
    public List<EmpregadoraResponse> buscarPorCargo(String cargo) {
        logger.info("Buscando empregadoras por cargo: {}", cargo);
        List<Empregadora> empregadoras = empregadoraRepository.findByCargoContaining(cargo);
        return empregadoras.stream()
                .map(EmpregadoraResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar empregadoras com maior rendimento
    @Transactional(readOnly = true)
    public List<EmpregadoraResponse> buscarComMaiorRendimento() {
        logger.info("Buscando empregadoras com maior rendimento");
        List<Empregadora> empregadoras = empregadoraRepository.findEmpregadorasComMaiorRendimento();
        return empregadoras.stream()
                .map(EmpregadoraResponse::new)
                .collect(Collectors.toList());
    }

    // Contar empregadoras por cliente
    @Transactional(readOnly = true)
    public Long contarPorCliente(Long clienteId) {
        logger.info("Contando empregadoras do cliente ID: {}", clienteId);
        return empregadoraRepository.countByClienteId(clienteId);
    }
}
