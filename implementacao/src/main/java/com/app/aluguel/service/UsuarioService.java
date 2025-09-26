package com.app.aluguel.service;

import com.app.aluguel.model.Usuario;
import com.app.aluguel.model.TipoUsuario;
import com.app.aluguel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // @Autowired
    // private PasswordEncoder passwordEncoder;
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public List<Usuario> listarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }
    
    public List<Usuario> listarPorTipo(TipoUsuario tipo) {
        return usuarioRepository.findByTipo(tipo);
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findActiveByUsername(username);
    }
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findActiveByEmail(email);
    }
    
    public Usuario salvar(Usuario usuario) {
        // Salvar senha sem criptografia por enquanto
        return usuarioRepository.save(usuario);
    }
    
    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
    
    public boolean autenticar(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findActiveByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Comparação simples de senha por enquanto
            if (password.equals(usuario.getPassword())) {
                usuario.setUltimoAcesso(LocalDateTime.now());
                usuarioRepository.save(usuario);
                return true;
            }
        }
        return false;
    }
    
    public boolean existeUsername(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
    
    public Usuario criarUsuario(String username, String email, String password, String nomeCompleto, TipoUsuario tipo) {
        if (existeUsername(username)) {
            throw new RuntimeException("Username já existe");
        }
        if (existeEmail(email)) {
            throw new RuntimeException("Email já existe");
        }
        
        Usuario usuario = new Usuario(username, email, password, nomeCompleto, tipo);
        return salvar(usuario);
    }
}
