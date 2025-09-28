package com.xumblaubs.service;

import com.xumblaubs.dto.BancoRequest;
import com.xumblaubs.dto.BancoResponse;
import com.xumblaubs.entity.Banco;
import com.xumblaubs.repository.BancoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BancoService {

    private static final Logger logger = LoggerFactory.getLogger(BancoService.class);

    @Autowired
    private BancoRepository bancoRepository;

    // Listar todos os bancos
    @Transactional(readOnly = true)
    public List<BancoResponse> listarTodos() {
        logger.info("Listando todos os bancos");
        List<Banco> bancos = bancoRepository.findByAtivoTrueOrderByNomeAsc();
        return bancos.stream()
                .map(BancoResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar banco por ID
    @Transactional(readOnly = true)
    public Optional<BancoResponse> buscarPorId(Long id) {
        logger.info("Buscando banco ID: {}", id);
        return bancoRepository.findById(id)
                .filter(Banco::getAtivo)
                .map(BancoResponse::new);
    }

    // Buscar banco por código
    @Transactional(readOnly = true)
    public Optional<BancoResponse> buscarPorCodigo(String codigo) {
        logger.info("Buscando banco por código: {}", codigo);
        return bancoRepository.findByCodigoAndAtivoTrue(codigo)
                .map(BancoResponse::new);
    }

    // Buscar bancos por nome
    @Transactional(readOnly = true)
    public List<BancoResponse> buscarPorNome(String nome) {
        logger.info("Buscando bancos por nome: {}", nome);
        List<Banco> bancos = bancoRepository.findByNomeContainingIgnoreCaseAndAtivoTrueOrderByNomeAsc(nome);
        return bancos.stream()
                .map(BancoResponse::new)
                .collect(Collectors.toList());
    }

    // Criar novo banco
    public BancoResponse criarBanco(BancoRequest request) {
        logger.info("Criando novo banco: {}", request);

        // Verificar se código já existe
        if (bancoRepository.existsByCodigoAndAtivoTrue(request.getCodigo())) {
            throw new RuntimeException("Código do banco já existe: " + request.getCodigo());
        }

        // Verificar se CNPJ já existe
        if (bancoRepository.existsByCnpjAndAtivoTrue(request.getCnpj())) {
            throw new RuntimeException("CNPJ já existe: " + request.getCnpj());
        }

        // Criar banco
        Banco banco = new Banco();
        banco.setNome(request.getNome());
        banco.setCodigo(request.getCodigo());
        banco.setCnpj(request.getCnpj());
        banco.setEndereco(request.getEndereco());
        banco.setTelefone(request.getTelefone());
        banco.setEmail(request.getEmail());
        banco.setTaxaJurosPadrao(request.getTaxaJurosPadrao());
        banco.setLimiteCreditoMaximo(request.getLimiteCreditoMaximo());
        banco.setAtivo(true);

        // Salvar banco
        banco = bancoRepository.save(banco);
        logger.info("Banco criado com sucesso: {}", banco.getId());

        return new BancoResponse(banco);
    }

    // Atualizar banco
    public BancoResponse atualizarBanco(Long id, BancoRequest request) {
        logger.info("Atualizando banco ID: {} com dados: {}", id, request);

        Banco banco = bancoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banco não encontrado com ID: " + id));

        if (!banco.getAtivo()) {
            throw new RuntimeException("Banco está inativo");
        }

        // Verificar se código já existe (exceto para o próprio banco)
        Optional<Banco> bancoExistente = bancoRepository.findByCodigoAndAtivoTrue(request.getCodigo());
        if (bancoExistente.isPresent() && !bancoExistente.get().getId().equals(id)) {
            throw new RuntimeException("Código do banco já existe: " + request.getCodigo());
        }

        // Verificar se CNPJ já existe (exceto para o próprio banco)
        Optional<Banco> bancoCnpjExistente = bancoRepository.findByCnpjAndAtivoTrue(request.getCnpj());
        if (bancoCnpjExistente.isPresent() && !bancoCnpjExistente.get().getId().equals(id)) {
            throw new RuntimeException("CNPJ já existe: " + request.getCnpj());
        }

        // Atualizar dados
        banco.setNome(request.getNome());
        banco.setCodigo(request.getCodigo());
        banco.setCnpj(request.getCnpj());
        banco.setEndereco(request.getEndereco());
        banco.setTelefone(request.getTelefone());
        banco.setEmail(request.getEmail());
        banco.setTaxaJurosPadrao(request.getTaxaJurosPadrao());
        banco.setLimiteCreditoMaximo(request.getLimiteCreditoMaximo());

        // Salvar alterações
        banco = bancoRepository.save(banco);
        logger.info("Banco atualizado com sucesso: {}", banco.getId());

        return new BancoResponse(banco);
    }

    // Desativar banco (soft delete)
    public void desativarBanco(Long id) {
        logger.info("Desativando banco ID: {}", id);

        Banco banco = bancoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banco não encontrado com ID: " + id));

        // Verificar se banco possui contratos ativos
        Long contratosAtivos = bancoRepository.countContratosByBanco(id);
        if (contratosAtivos > 0) {
            throw new RuntimeException("Não é possível desativar banco com contratos ativos. Contratos ativos: " + contratosAtivos);
        }

        banco.setAtivo(false);
        bancoRepository.save(banco);
        logger.info("Banco desativado com sucesso: {}", banco.getId());
    }

    // Ativar banco
    public BancoResponse ativarBanco(Long id) {
        logger.info("Ativando banco ID: {}", id);

        Banco banco = bancoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banco não encontrado com ID: " + id));

        banco.setAtivo(true);
        banco = bancoRepository.save(banco);
        logger.info("Banco ativado com sucesso: {}", banco.getId());

        return new BancoResponse(banco);
    }

    // Buscar bancos com limite disponível
    @Transactional(readOnly = true)
    public List<BancoResponse> buscarBancosComLimiteDisponivel() {
        logger.info("Buscando bancos com limite de crédito disponível");
        List<Banco> bancos = bancoRepository.findBancosComLimiteDisponivel();
        return bancos.stream()
                .map(BancoResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar bancos por taxa de juros
    @Transactional(readOnly = true)
    public List<BancoResponse> buscarPorTaxaJuros(Double taxaMin, Double taxaMax) {
        logger.info("Buscando bancos por taxa de juros entre {} e {}", taxaMin, taxaMax);
        List<Banco> bancos = bancoRepository.findByTaxaJurosBetween(taxaMin, taxaMax);
        return bancos.stream()
                .map(BancoResponse::new)
                .collect(Collectors.toList());
    }

    // Buscar bancos com mais contratos
    @Transactional(readOnly = true)
    public List<BancoResponse> buscarBancosComMaisContratos() {
        logger.info("Buscando bancos com mais contratos");
        List<Banco> bancos = bancoRepository.findBancosComMaisContratos();
        return bancos.stream()
                .map(BancoResponse::new)
                .collect(Collectors.toList());
    }
}
