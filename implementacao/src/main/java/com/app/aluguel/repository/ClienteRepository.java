package com.app.aluguel.repository;

import com.app.aluguel.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    Optional<Cliente> findByRg(String rg);
    
    Optional<Cliente> findByEmail(String email);
    
    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")
    List<Cliente> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT c FROM Cliente c WHERE c.profissao = :profissao")
    List<Cliente> findByProfissao(@Param("profissao") String profissao);
}
