package com.xumblaubs.service;

import com.xumblaubs.dto.ClienteRequest;
import com.xumblaubs.dto.ClienteResponse;
import com.xumblaubs.entity.Cliente;
import com.xumblaubs.entity.User;
import com.xumblaubs.entity.Role;
import com.xumblaubs.repository.ClienteRepository;
import com.xumblaubs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
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
    
    public Optional<ClienteResponse> findByRg(String rg) {
        return clienteRepository.findByRg(rg)
                .map(ClienteResponse::new);
    }
    
    public List<ClienteResponse> findByNomeContaining(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(ClienteResponse::new)
                .collect(Collectors.toList());
    }
    
    public ClienteResponse create(ClienteRequest request) {
        // Verificar se CPF já existe
        if (clienteRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + request.getCpf());
        }
        
        // Verificar se RG já existe
        if (clienteRepository.existsByRg(request.getRg())) {
            throw new RuntimeException("RG já cadastrado: " + request.getRg());
        }
        
        // Verificar se email já existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + request.getEmail());
        }
        
        // Criar cliente
        Cliente cliente = new Cliente();
        cliente.setRg(request.getRg());
        cliente.setCpf(request.getCpf());
        cliente.setNome(request.getNome());
        cliente.setEndereco(request.getEndereco());
        cliente.setProfissao(request.getProfissao());
        cliente.setEmail(request.getEmail());
        cliente.setSenha(passwordEncoder.encode(request.getSenha()));
        cliente.setAtivo(true);
        
        Cliente savedCliente = clienteRepository.save(cliente);
        
        // Criar usuário correspondente
        User user = new User();
        user.setName(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getSenha()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        
        userRepository.save(user);
        
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
                    
                    // Verificar se RG já existe em outro cliente
                    if (!cliente.getRg().equals(request.getRg()) && 
                        clienteRepository.existsByRg(request.getRg())) {
                        throw new RuntimeException("RG já cadastrado: " + request.getRg());
                    }
                    
                    // Verificar se email já existe em outro usuário
                    if (!cliente.getEmail().equals(request.getEmail()) && 
                        userRepository.existsByEmail(request.getEmail())) {
                        throw new RuntimeException("Email já cadastrado: " + request.getEmail());
                    }
                    
                    cliente.setRg(request.getRg());
                    cliente.setCpf(request.getCpf());
                    cliente.setNome(request.getNome());
                    cliente.setEndereco(request.getEndereco());
                    cliente.setProfissao(request.getProfissao());
                    cliente.setEmail(request.getEmail());
                    cliente.setSenha(passwordEncoder.encode(request.getSenha()));
                    
                    Cliente updatedCliente = clienteRepository.save(cliente);
                    
                    // Atualizar usuário correspondente
                    userRepository.findByEmail(cliente.getEmail()).ifPresent(user -> {
                        user.setName(request.getNome());
                        user.setEmail(request.getEmail());
                        user.setPassword(passwordEncoder.encode(request.getSenha()));
                        userRepository.save(user);
                    });
                    
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
