package com.xumblaubs.service;

import com.xumblaubs.dto.AutomovelRequest;
import com.xumblaubs.dto.AutomovelResponse;
import com.xumblaubs.entity.Automovel;
import com.xumblaubs.entity.TipoProprietario;
import com.xumblaubs.repository.AutomovelRepository;
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
public class AutomovelService {
    
    private static final Logger logger = LoggerFactory.getLogger(AutomovelService.class);
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    // Criar novo automóvel
    public AutomovelResponse criarAutomovel(AutomovelRequest request) {
        logger.info("Criando novo automóvel: {} {}", request.getMarca(), request.getModelo());
        
        // Verificar se matrícula já existe
        if (automovelRepository.existsByMatricula(request.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada: " + request.getMatricula());
        }
        
        // Verificar se placa já existe
        if (automovelRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Placa já cadastrada: " + request.getPlaca());
        }
        
        Automovel automovel = new Automovel();
        automovel.setMatricula(request.getMatricula());
        automovel.setAno(request.getAno());
        automovel.setMarca(request.getMarca());
        automovel.setModelo(request.getModelo());
        automovel.setPlaca(request.getPlaca());
        automovel.setTipoProprietario(request.getTipoProprietario());
        automovel.setProprietarioId(request.getProprietarioId());
        automovel.setProprietarioNome(request.getProprietarioNome());
        automovel.setObservacoes(request.getObservacoes());
        automovel.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        
        Automovel automovelSalvo = automovelRepository.save(automovel);
        logger.info("Automóvel criado com sucesso. ID: {}", automovelSalvo.getId());
        
        return converterParaResponse(automovelSalvo);
    }
    
    // Buscar todos os automóveis
    @Transactional(readOnly = true)
    public List<AutomovelResponse> listarTodos() {
        logger.info("Listando todos os automóveis");
        List<Automovel> automoveis = automovelRepository.findAll();
        return automoveis.stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }
    
    // Buscar automóveis ativos
    @Transactional(readOnly = true)
    public List<AutomovelResponse> listarAtivos() {
        logger.info("Listando automóveis ativos");
        List<Automovel> automoveis = automovelRepository.findByAtivoTrue();
        return automoveis.stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }
    
    // Buscar por ID
    @Transactional(readOnly = true)
    public AutomovelResponse buscarPorId(Long id) {
        logger.info("Buscando automóvel por ID: {}", id);
        Optional<Automovel> automovel = automovelRepository.findById(id);
        if (automovel.isPresent()) {
            return converterParaResponse(automovel.get());
        }
        throw new RuntimeException("Automóvel não encontrado com ID: " + id);
    }
    
    // Buscar por matrícula
    @Transactional(readOnly = true)
    public AutomovelResponse buscarPorMatricula(String matricula) {
        logger.info("Buscando automóvel por matrícula: {}", matricula);
        Optional<Automovel> automovel = automovelRepository.findByMatricula(matricula);
        if (automovel.isPresent()) {
            return converterParaResponse(automovel.get());
        }
        throw new RuntimeException("Automóvel não encontrado com matrícula: " + matricula);
    }
    
    // Buscar por placa
    @Transactional(readOnly = true)
    public AutomovelResponse buscarPorPlaca(String placa) {
        logger.info("Buscando automóvel por placa: {}", placa);
        Optional<Automovel> automovel = automovelRepository.findByPlaca(placa);
        if (automovel.isPresent()) {
            return converterParaResponse(automovel.get());
        }
        throw new RuntimeException("Automóvel não encontrado com placa: " + placa);
    }
    
    // Buscar por filtros
    @Transactional(readOnly = true)
    public List<AutomovelResponse> buscarPorFiltros(String marca, String modelo, Integer ano, 
                                                   TipoProprietario tipoProprietario, Boolean ativo) {
        logger.info("Buscando automóveis por filtros - Marca: {}, Modelo: {}, Ano: {}, Tipo: {}, Ativo: {}", 
                   marca, modelo, ano, tipoProprietario, ativo);
        
        List<Automovel> automoveis = automovelRepository.findByFiltros(marca, modelo, ano, tipoProprietario, ativo);
        return automoveis.stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }
    
    // Atualizar automóvel
    public AutomovelResponse atualizarAutomovel(Long id, AutomovelRequest request) {
        logger.info("Atualizando automóvel ID: {}", id);
        
        Optional<Automovel> automovelOpt = automovelRepository.findById(id);
        if (!automovelOpt.isPresent()) {
            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
        }
        
        Automovel automovel = automovelOpt.get();
        
        // Verificar se matrícula já existe em outro automóvel
        if (!automovel.getMatricula().equals(request.getMatricula()) && 
            automovelRepository.existsByMatricula(request.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada: " + request.getMatricula());
        }
        
        // Verificar se placa já existe em outro automóvel
        if (!automovel.getPlaca().equals(request.getPlaca()) && 
            automovelRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Placa já cadastrada: " + request.getPlaca());
        }
        
        automovel.setMatricula(request.getMatricula());
        automovel.setAno(request.getAno());
        automovel.setMarca(request.getMarca());
        automovel.setModelo(request.getModelo());
        automovel.setPlaca(request.getPlaca());
        automovel.setTipoProprietario(request.getTipoProprietario());
        automovel.setProprietarioId(request.getProprietarioId());
        automovel.setProprietarioNome(request.getProprietarioNome());
        automovel.setObservacoes(request.getObservacoes());
        if (request.getAtivo() != null) {
            automovel.setAtivo(request.getAtivo());
        }
        
        Automovel automovelAtualizado = automovelRepository.save(automovel);
        logger.info("Automóvel atualizado com sucesso. ID: {}", automovelAtualizado.getId());
        
        return converterParaResponse(automovelAtualizado);
    }
    
    // Desativar automóvel (soft delete)
    public void desativarAutomovel(Long id) {
        logger.info("Desativando automóvel ID: {}", id);
        
        Optional<Automovel> automovelOpt = automovelRepository.findById(id);
        if (!automovelOpt.isPresent()) {
            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
        }
        
        Automovel automovel = automovelOpt.get();
        automovel.setAtivo(false);
        automovelRepository.save(automovel);
        
        logger.info("Automóvel desativado com sucesso. ID: {}", id);
    }
    
    // Ativar automóvel
    public void ativarAutomovel(Long id) {
        logger.info("Ativando automóvel ID: {}", id);
        
        Optional<Automovel> automovelOpt = automovelRepository.findById(id);
        if (!automovelOpt.isPresent()) {
            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
        }
        
        Automovel automovel = automovelOpt.get();
        automovel.setAtivo(true);
        automovelRepository.save(automovel);
        
        logger.info("Automóvel ativado com sucesso. ID: {}", id);
    }
    
    // Deletar automóvel (hard delete)
    public void deletarAutomovel(Long id) {
        logger.info("Deletando automóvel ID: {}", id);
        
        if (!automovelRepository.existsById(id)) {
            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
        }
        
        automovelRepository.deleteById(id);
        logger.info("Automóvel deletado com sucesso. ID: {}", id);
    }
    
    // Buscar automóveis disponíveis para aluguel
    @Transactional(readOnly = true)
    public List<AutomovelResponse> buscarDisponiveisParaAluguel() {
        logger.info("Buscando automóveis disponíveis para aluguel");
        List<Automovel> automoveis = automovelRepository.findDisponiveisParaAluguel();
        return automoveis.stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }
    
    // Contar automóveis por tipo de proprietário
    @Transactional(readOnly = true)
    public List<Object[]> contarPorTipoProprietario() {
        logger.info("Contando automóveis por tipo de proprietário");
        return automovelRepository.countByTipoProprietario();
    }
    
    // Converter Automovel para AutomovelResponse
    private AutomovelResponse converterParaResponse(Automovel automovel) {
        AutomovelResponse response = new AutomovelResponse();
        response.setId(automovel.getId());
        response.setMatricula(automovel.getMatricula());
        response.setAno(automovel.getAno());
        response.setMarca(automovel.getMarca());
        response.setModelo(automovel.getModelo());
        response.setPlaca(automovel.getPlaca());
        response.setTipoProprietario(automovel.getTipoProprietario());
        response.setProprietarioId(automovel.getProprietarioId());
        response.setProprietarioNome(automovel.getProprietarioNome());
        response.setObservacoes(automovel.getObservacoes());
        response.setAtivo(automovel.getAtivo());
        response.setCreatedAt(automovel.getCreatedAt());
        response.setUpdatedAt(automovel.getUpdatedAt());
        return response;
    }
}
