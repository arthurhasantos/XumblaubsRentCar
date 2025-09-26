package com.app.aluguel.controller;

import com.app.aluguel.model.Automovel;
import com.app.aluguel.model.StatusAutomovel;
import com.app.aluguel.model.TipoContrato;
import com.app.aluguel.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/automoveis")
@Validated
public class AutomovelController {
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    @GetMapping
    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }
    
    @GetMapping("/disponiveis")
    public List<Automovel> listarDisponiveis() {
        return automovelRepository.findByStatus(StatusAutomovel.DISPONIVEL);
    }
    
    @GetMapping("/status/{status}")
    public List<Automovel> listarPorStatus(@PathVariable StatusAutomovel status) {
        return automovelRepository.findByStatus(status);
    }
    
    @GetMapping("/tipo/{tipo}")
    public List<Automovel> listarPorTipo(@PathVariable TipoContrato tipo) {
        return automovelRepository.findByTipoContrato(tipo);
    }
    
    @GetMapping("/marca/{marca}")
    public List<Automovel> listarPorMarca(@PathVariable String marca) {
        return automovelRepository.findByMarca(marca);
    }
    
    @GetMapping("/modelo/{modelo}")
    public List<Automovel> listarPorModelo(@PathVariable String modelo) {
        return automovelRepository.findByModelo(modelo);
    }
    
    @GetMapping("/ano/{ano}")
    public List<Automovel> listarPorAno(@PathVariable Integer ano) {
        return automovelRepository.findByAno(ano);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Automovel> buscarPorId(@PathVariable Long id) {
        Optional<Automovel> automovel = automovelRepository.findById(id);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Automovel> criar(@Valid @RequestBody Automovel automovel) {
        Automovel automovelSalvo = automovelRepository.save(automovel);
        return ResponseEntity.ok(automovelSalvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Automovel> atualizar(@PathVariable Long id, @Valid @RequestBody Automovel automovel) {
        Optional<Automovel> automovelExistente = automovelRepository.findById(id);
        if (automovelExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        automovel.setId(id);
        Automovel automovelAtualizado = automovelRepository.save(automovel);
        return ResponseEntity.ok(automovelAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Automovel> automovel = automovelRepository.findById(id);
        if (automovel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        automovelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Automovel> atualizarStatus(@PathVariable Long id, @RequestParam StatusAutomovel status) {
        Optional<Automovel> automovelOpt = automovelRepository.findById(id);
        if (automovelOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Automovel automovel = automovelOpt.get();
        automovel.setStatus(status);
        Automovel automovelAtualizado = automovelRepository.save(automovel);
        return ResponseEntity.ok(automovelAtualizado);
    }
}
