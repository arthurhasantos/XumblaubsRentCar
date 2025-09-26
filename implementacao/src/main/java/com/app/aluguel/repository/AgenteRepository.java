package com.app.aluguel.repository;

import com.app.aluguel.model.Agente;
import com.app.aluguel.model.TipoAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    
    Optional<Agente> findByCnpj(String cnpj);
    
    List<Agente> findByTipo(TipoAgente tipo);
    
    @Query("SELECT a FROM Agente a WHERE a.nome LIKE %:nome%")
    List<Agente> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT a FROM Agente a WHERE a.tipo = :tipo AND a.nome LIKE %:nome%")
    List<Agente> findByTipoAndNomeContaining(@Param("tipo") TipoAgente tipo, @Param("nome") String nome);
}
