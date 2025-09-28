package com.xumblaubs.repository;

import com.xumblaubs.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

    // Buscar bancos ativos
    List<Banco> findByAtivoTrueOrderByNomeAsc();

    // Buscar banco por código
    Optional<Banco> findByCodigoAndAtivoTrue(String codigo);

    // Buscar banco por CNPJ
    Optional<Banco> findByCnpjAndAtivoTrue(String cnpj);

    // Buscar banco por nome
    List<Banco> findByNomeContainingIgnoreCaseAndAtivoTrueOrderByNomeAsc(String nome);

    // Verificar se código já existe
    boolean existsByCodigoAndAtivoTrue(String codigo);

    // Verificar se CNPJ já existe
    boolean existsByCnpjAndAtivoTrue(String cnpj);

    // Buscar bancos com limite de crédito disponível
    @Query("SELECT b FROM Banco b WHERE b.limiteCreditoMaximo IS NULL OR b.limiteCreditoMaximo > 0 AND b.ativo = true ORDER BY b.nome ASC")
    List<Banco> findBancosComLimiteDisponivel();

    // Buscar bancos por taxa de juros (range)
    @Query("SELECT b FROM Banco b WHERE b.taxaJurosPadrao BETWEEN :taxaMin AND :taxaMax AND b.ativo = true ORDER BY b.taxaJurosPadrao ASC")
    List<Banco> findByTaxaJurosBetween(@Param("taxaMin") Double taxaMin, @Param("taxaMax") Double taxaMax);

    // Contar contratos de crédito por banco
    @Query("SELECT COUNT(c) FROM ContratoCredito c WHERE c.banco.id = :bancoId AND c.ativo = true")
    Long countContratosByBanco(@Param("bancoId") Long bancoId);

    // Buscar bancos com mais contratos
    @Query("SELECT b FROM Banco b LEFT JOIN b.contratosCredito c WHERE c.ativo = true GROUP BY b.id ORDER BY COUNT(c) DESC")
    List<Banco> findBancosComMaisContratos();

    // Buscar bancos por cidade (usando endereço)
    @Query("SELECT b FROM Banco b WHERE LOWER(b.endereco) LIKE LOWER(CONCAT('%', :cidade, '%')) AND b.ativo = true ORDER BY b.nome ASC")
    List<Banco> findByCidade(@Param("cidade") String cidade);

    // Buscar bancos com telefone
    @Query("SELECT b FROM Banco b WHERE b.telefone IS NOT NULL AND b.telefone != '' AND b.ativo = true ORDER BY b.nome ASC")
    List<Banco> findBancosComTelefone();

    // Buscar bancos com email
    @Query("SELECT b FROM Banco b WHERE b.email IS NOT NULL AND b.email != '' AND b.ativo = true ORDER BY b.nome ASC")
    List<Banco> findBancosComEmail();
}
