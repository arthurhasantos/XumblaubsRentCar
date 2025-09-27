package com.xumblaubs.repository;

import com.xumblaubs.entity.Automovel;
import com.xumblaubs.entity.TipoProprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    
    // Buscar por matrícula
    Optional<Automovel> findByMatricula(String matricula);
    
    // Buscar por placa
    Optional<Automovel> findByPlaca(String placa);
    
    // Verificar se matrícula existe
    boolean existsByMatricula(String matricula);
    
    // Verificar se placa existe
    boolean existsByPlaca(String placa);
    
    // Buscar automóveis ativos
    List<Automovel> findByAtivoTrue();
    
    // Buscar automóveis inativos
    List<Automovel> findByAtivoFalse();
    
    // Buscar por tipo de proprietário
    List<Automovel> findByTipoProprietario(TipoProprietario tipoProprietario);
    
    // Buscar por marca
    List<Automovel> findByMarcaContainingIgnoreCase(String marca);
    
    // Buscar por modelo
    List<Automovel> findByModeloContainingIgnoreCase(String modelo);
    
    // Buscar por ano
    List<Automovel> findByAno(Integer ano);
    
    // Buscar por ano entre
    List<Automovel> findByAnoBetween(Integer anoInicio, Integer anoFim);
    
    // Buscar por proprietário
    List<Automovel> findByProprietarioId(Long proprietarioId);
    
    // Buscar por proprietário e tipo
    List<Automovel> findByProprietarioIdAndTipoProprietario(Long proprietarioId, TipoProprietario tipoProprietario);
    
    // Busca personalizada com filtros múltiplos
    @Query("SELECT a FROM Automovel a WHERE " +
           "(:marca IS NULL OR LOWER(a.marca) LIKE LOWER(CONCAT('%', :marca, '%'))) AND " +
           "(:modelo IS NULL OR LOWER(a.modelo) LIKE LOWER(CONCAT('%', :modelo, '%'))) AND " +
           "(:ano IS NULL OR a.ano = :ano) AND " +
           "(:tipoProprietario IS NULL OR a.tipoProprietario = :tipoProprietario) AND " +
           "(:ativo IS NULL OR a.ativo = :ativo)")
    List<Automovel> findByFiltros(@Param("marca") String marca,
                                 @Param("modelo") String modelo,
                                 @Param("ano") Integer ano,
                                 @Param("tipoProprietario") TipoProprietario tipoProprietario,
                                 @Param("ativo") Boolean ativo);
    
    // Contar automóveis por tipo de proprietário
    @Query("SELECT a.tipoProprietario, COUNT(a) FROM Automovel a WHERE a.ativo = true GROUP BY a.tipoProprietario")
    List<Object[]> countByTipoProprietario();
    
    // Buscar automóveis disponíveis para aluguel (todos os ativos por enquanto)
    @Query("SELECT a FROM Automovel a WHERE a.ativo = true")
    List<Automovel> findDisponiveisParaAluguel();
}
