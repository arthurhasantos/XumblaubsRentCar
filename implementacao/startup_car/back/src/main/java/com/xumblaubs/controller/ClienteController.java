package com.xumblaubs.controller;

import com.xumblaubs.dto.ClienteRequest;
import com.xumblaubs.dto.ClienteResponse;
import com.xumblaubs.dto.MessageResponse;
import com.xumblaubs.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAllClientes(
            @RequestParam(defaultValue = "false") boolean incluirInativos) {
        List<ClienteResponse> clientes = incluirInativos ? 
                clienteService.findAll() : 
                clienteService.findAllAtivos();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Long id) {
        Optional<ClienteResponse> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponse> getClienteByCpf(@PathVariable String cpf) {
        Optional<ClienteResponse> cliente = clienteService.findByCpf(cpf);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/rg/{rg}")
    public ResponseEntity<ClienteResponse> getClienteByRg(@PathVariable String rg) {
        Optional<ClienteResponse> cliente = clienteService.findByRg(rg);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteResponse>> buscarClientesPorNome(
            @RequestParam String nome) {
        List<ClienteResponse> clientes = clienteService.findByNomeContaining(nome);
        return ResponseEntity.ok(clientes);
    }
    
    @PostMapping
    public ResponseEntity<?> createCliente(@Valid @RequestBody ClienteRequest request) {
        try {
            ClienteResponse cliente = clienteService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, 
                                         @Valid @RequestBody ClienteRequest request) {
        try {
            Optional<ClienteResponse> cliente = clienteService.update(id, request);
            return cliente.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Erro: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCliente(@PathVariable Long id) {
        boolean deleted = clienteService.delete(id);
        if (deleted) {
            return ResponseEntity.ok(new MessageResponse("Cliente desativado com sucesso!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<MessageResponse> hardDeleteCliente(@PathVariable Long id) {
        boolean deleted = clienteService.hardDelete(id);
        if (deleted) {
            return ResponseEntity.ok(new MessageResponse("Cliente removido permanentemente!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/ativar")
    public ResponseEntity<MessageResponse> activateCliente(@PathVariable Long id) {
        boolean activated = clienteService.activate(id);
        if (activated) {
            return ResponseEntity.ok(new MessageResponse("Cliente ativado com sucesso!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
