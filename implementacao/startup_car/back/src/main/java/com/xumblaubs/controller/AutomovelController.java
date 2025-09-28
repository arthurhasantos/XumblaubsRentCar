package com.xumblaubs.controller;

import com.xumblaubs.dto.AutomovelRequest;
import com.xumblaubs.dto.AutomovelResponse;
import com.xumblaubs.dto.MessageResponse;
import com.xumblaubs.entity.TipoProprietario;
import com.xumblaubs.service.AutomovelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/automoveis")
public class AutomovelController {
    
    private static final Logger logger = LoggerFactory.getLogger(AutomovelController.class);
    
    @Autowired
    private AutomovelService automovelService;
    
    // Criar novo automóvel - PÚBLICO
    @PostMapping
    public ResponseEntity<?> criarAutomovel(@Valid @RequestBody AutomovelRequest request) {
        try {
            logger.info("Admin criando novo automóvel: {} {}", request.getMarca(), request.getModelo());
            AutomovelResponse response = automovelService.criarAutomovel(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Erro ao criar automóvel: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro ao criar automóvel: " + e.getMessage()));
        }
    }
    
    // Listar todos os automóveis - PÚBLICO (para visualização)
    @GetMapping
    public ResponseEntity<List<AutomovelResponse>> listarTodos() {
        logger.info("Listando todos os automóveis");
        List<AutomovelResponse> automoveis = automovelService.listarTodos();
        return ResponseEntity.ok(automoveis);
    }
    
    // Listar automóveis ativos - PÚBLICO
    @GetMapping("/ativos")
    public ResponseEntity<List<AutomovelResponse>> listarAtivos() {
        logger.info("Listando automóveis ativos");
        List<AutomovelResponse> automoveis = automovelService.listarAtivos();
        return ResponseEntity.ok(automoveis);
    }
    
    // Buscar por ID - PÚBLICO
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            logger.info("Buscando automóvel por ID: {}", id);
            AutomovelResponse response = automovelService.buscarPorId(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao buscar automóvel por ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    // Buscar por matrícula - PÚBLICO
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<?> buscarPorMatricula(@PathVariable String matricula) {
        try {
            logger.info("Buscando automóvel por matrícula: {}", matricula);
            AutomovelResponse response = automovelService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao buscar automóvel por matrícula {}: {}", matricula, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    // Buscar por placa - PÚBLICO
    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        try {
            logger.info("Buscando automóvel por placa: {}", placa);
            AutomovelResponse response = automovelService.buscarPorPlaca(placa);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao buscar automóvel por placa {}: {}", placa, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    // Buscar por filtros - PÚBLICO
    @GetMapping("/buscar")
    public ResponseEntity<List<AutomovelResponse>> buscarPorFiltros(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) TipoProprietario tipoProprietario,
            @RequestParam(required = false) Boolean ativo) {
        
        logger.info("Buscando automóveis por filtros");
        List<AutomovelResponse> automoveis = automovelService.buscarPorFiltros(
                marca, modelo, ano, tipoProprietario, ativo);
        return ResponseEntity.ok(automoveis);
    }
    
    // Buscar disponíveis para aluguel - PÚBLICO
    @GetMapping("/disponiveis")
    public ResponseEntity<List<AutomovelResponse>> buscarDisponiveisParaAluguel() {
        logger.info("Buscando automóveis disponíveis para aluguel");
        List<AutomovelResponse> automoveis = automovelService.buscarDisponiveisParaAluguel();
        return ResponseEntity.ok(automoveis);
    }
    
    // Atualizar automóvel - APENAS ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarAutomovel(@PathVariable Long id, 
                                              @Valid @RequestBody AutomovelRequest request) {
        try {
            logger.info("Admin atualizando automóvel ID: {}", id);
            AutomovelResponse response = automovelService.atualizarAutomovel(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao atualizar automóvel ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro ao atualizar automóvel: " + e.getMessage()));
        }
    }
    
    // Desativar automóvel - APENAS ADMIN
    @PutMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desativarAutomovel(@PathVariable Long id) {
        try {
            logger.info("Admin desativando automóvel ID: {}", id);
            automovelService.desativarAutomovel(id);
            return ResponseEntity.ok(new MessageResponse("Automóvel desativado com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao desativar automóvel ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro ao desativar automóvel: " + e.getMessage()));
        }
    }
    
    // Ativar automóvel - APENAS ADMIN
    @PutMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ativarAutomovel(@PathVariable Long id) {
        try {
            logger.info("Admin ativando automóvel ID: {}", id);
            automovelService.ativarAutomovel(id);
            return ResponseEntity.ok(new MessageResponse("Automóvel ativado com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao ativar automóvel ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro ao ativar automóvel: " + e.getMessage()));
        }
    }
    
    // Deletar automóvel - APENAS ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarAutomovel(@PathVariable Long id) {
        try {
            logger.info("Admin deletando automóvel ID: {}", id);
            automovelService.deletarAutomovel(id);
            return ResponseEntity.ok(new MessageResponse("Automóvel deletado com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao deletar automóvel ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro ao deletar automóvel: " + e.getMessage()));
        }
    }
    
    // Estatísticas - APENAS ADMIN
    @GetMapping("/estatisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obterEstatisticas() {
        try {
            logger.info("Admin solicitando estatísticas de automóveis");
            List<Object[]> estatisticas = automovelService.contarPorTipoProprietario();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            logger.error("Erro ao obter estatísticas: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro ao obter estatísticas: " + e.getMessage()));
        }
    }
}
