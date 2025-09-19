package com.sismop.repository;

import com.sismop.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    Optional<Cliente> findByEmail(String email);
    
    Boolean existsByCpf(String cpf);
    
    Boolean existsByEmail(String email);
    
    List<Cliente> findByAtivoTrue();
    
    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:nome% AND c.ativo = true")
    List<Cliente> findByNomeContainingAndAtivoTrue(@Param("nome") String nome);
    
    @Query("SELECT c FROM Cliente c WHERE c.cpf LIKE %:cpf% AND c.ativo = true")
    List<Cliente> findByCpfContainingAndAtivoTrue(@Param("cpf") String cpf);
}
