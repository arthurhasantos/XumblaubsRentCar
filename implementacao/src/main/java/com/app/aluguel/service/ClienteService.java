package com.app.aluguel.service;

import com.app.aluguel.model.Cliente;
import com.app.aluguel.model.EmpregoRenda;
import com.app.aluguel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
    
    public Optional<Cliente> buscarPorRg(String rg) {
        return clienteRepository.findByRg(rg);
    }
    
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContaining(nome);
    }
    
    public List<Cliente> buscarPorProfissao(String profissao) {
        return clienteRepository.findByProfissao(profissao);
    }
    
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public Cliente adicionarEmpregoRenda(Long clienteId, EmpregoRenda empregoRenda) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        cliente.adicionarEmpregoRenda(empregoRenda);
        return clienteRepository.save(cliente);
    }
    
    public boolean existeClienteComCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).isPresent();
    }
    
    public boolean existeClienteComRg(String rg) {
        return clienteRepository.findByRg(rg).isPresent();
    }
    
    public boolean existeClienteComEmail(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }
}
