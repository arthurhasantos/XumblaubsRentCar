package com.app.aluguel.repository;

import com.app.aluguel.model.Automovel;
import com.app.aluguel.model.StatusAutomovel;
import com.app.aluguel.model.TipoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    
    List<Automovel> findByStatus(StatusAutomovel status);
    
    List<Automovel> findByTipoContrato(TipoContrato tipoContrato);
    
    List<Automovel> findByMarca(String marca);
    
    List<Automovel> findByModelo(String modelo);
    
    List<Automovel> findByAno(Integer ano);
    
    @Query("SELECT a FROM Automovel a WHERE a.status = :status AND a.tipoContrato = :tipoContrato")
    List<Automovel> findByStatusAndTipoContrato(@Param("status") StatusAutomovel status, @Param("tipoContrato") TipoContrato tipoContrato);
    
    @Query("SELECT a FROM Automovel a WHERE a.marca = :marca AND a.modelo = :modelo")
    List<Automovel> findByMarcaAndModelo(@Param("marca") String marca, @Param("modelo") String modelo);
    
    @Query("SELECT a FROM Automovel a WHERE a.valorAluguel BETWEEN :valorMin AND :valorMax")
    List<Automovel> findByValorAluguelBetween(@Param("valorMin") Double valorMin, @Param("valorMax") Double valorMax);
}
