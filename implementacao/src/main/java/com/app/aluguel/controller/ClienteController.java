package com.app.aluguel.controller;

import com.app.aluguel.model.Cliente;
import com.app.aluguel.model.EmpregoRenda;
import com.app.aluguel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nome/{nome}")
    public List<Cliente> buscarPorNome(@PathVariable String nome) {
        return clienteService.buscarPorNome(nome);
    }
    
    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        if (clienteService.existeClienteComCpf(cliente.getCpf())) {
            return ResponseEntity.badRequest().build();
        }
        if (clienteService.existeClienteComRg(cliente.getRg())) {
            return ResponseEntity.badRequest().build();
        }
        if (cliente.getEmail() != null && clienteService.existeClienteComEmail(cliente.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteService.buscarPorId(id);
        if (clienteExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        cliente.setId(id);
        Cliente clienteAtualizado = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/empregos")
    public ResponseEntity<Cliente> adicionarEmpregoRenda(@PathVariable Long id, @Valid @RequestBody EmpregoRenda empregoRenda) {
        try {
            Cliente cliente = clienteService.adicionarEmpregoRenda(id, empregoRenda);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
