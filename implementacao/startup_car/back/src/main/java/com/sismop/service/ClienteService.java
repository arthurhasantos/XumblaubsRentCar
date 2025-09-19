package com.sismop.service;

import com.sismop.dto.ClienteRequest;
import com.sismop.dto.ClienteResponse;
import com.sismop.entity.Cliente;
import com.sismop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<ClienteResponse> findAllAtivos() {
        return clienteRepository.findByAtivoTrue().stream()
                .map(ClienteResponse::new)
                .collect(Collectors.toList());
    }
    
    public Optional<ClienteResponse> findById(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::new);
    }
    
    public Optional<ClienteResponse> findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(ClienteResponse::new);
    }
    
    public List<ClienteResponse> findByNomeContaining(String nome) {
        return clienteRepository.findByNomeContainingAndAtivoTrue(nome).stream()
                .map(ClienteResponse::new)
                .collect(Collectors.toList());
    }
    
    public ClienteResponse create(ClienteRequest request) {
        // Verificar se CPF já existe
        if (clienteRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + request.getCpf());
        }
        
        // Verificar se email já existe
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + request.getEmail());
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setTelefone(request.getTelefone());
        cliente.setEmail(request.getEmail());
        cliente.setEndereco(request.getEndereco());
        cliente.setAtivo(true);
        
        Cliente savedCliente = clienteRepository.save(cliente);
        return new ClienteResponse(savedCliente);
    }
    
    public Optional<ClienteResponse> update(Long id, ClienteRequest request) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    // Verificar se CPF já existe em outro cliente
                    if (!cliente.getCpf().equals(request.getCpf()) && 
                        clienteRepository.existsByCpf(request.getCpf())) {
                        throw new RuntimeException("CPF já cadastrado: " + request.getCpf());
                    }
                    
                    // Verificar se email já existe em outro cliente
                    if (!cliente.getEmail().equals(request.getEmail()) && 
                        clienteRepository.existsByEmail(request.getEmail())) {
                        throw new RuntimeException("Email já cadastrado: " + request.getEmail());
                    }
                    
                    cliente.setNome(request.getNome());
                    cliente.setCpf(request.getCpf());
                    cliente.setTelefone(request.getTelefone());
                    cliente.setEmail(request.getEmail());
                    cliente.setEndereco(request.getEndereco());
                    
                    Cliente updatedCliente = clienteRepository.save(cliente);
                    return new ClienteResponse(updatedCliente);
                });
    }
    
    public boolean delete(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setAtivo(false);
                    clienteRepository.save(cliente);
                    return true;
                })
                .orElse(false);
    }
    
    public boolean hardDelete(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean activate(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setAtivo(true);
                    clienteRepository.save(cliente);
                    return true;
                })
                .orElse(false);
    }
}
