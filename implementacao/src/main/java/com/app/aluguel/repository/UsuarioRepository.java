package com.app.aluguel.repository;

import com.app.aluguel.model.Usuario;
import com.app.aluguel.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByTipo(TipoUsuario tipo);
    
    List<Usuario> findByAtivoTrue();
    
    @Query("SELECT u FROM Usuario u WHERE u.username = :username AND u.ativo = true")
    Optional<Usuario> findActiveByUsername(@Param("username") String username);
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.ativo = true")
    Optional<Usuario> findActiveByEmail(@Param("email") String email);
}
