package com.xumblaubs.repository;

import com.xumblaubs.entity.Empregadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmpregadoraRepository extends JpaRepository<Empregadora, Long> {

    // Buscar empregadoras ativas
    List<Empregadora> findByAtivoTrueOrderByNomeAsc();

    // Buscar empregadoras por cliente
    List<Empregadora> findByClienteIdAndAtivoTrueOrderByNomeAsc(Long clienteId);

    // Buscar empregadora por nome
    List<Empregadora> findByNomeContainingIgnoreCaseAndAtivoTrueOrderByNomeAsc(String nome);

    // Buscar empregadoras por CNPJ
    List<Empregadora> findByCnpjAndAtivoTrue(String cnpj);

    // Verificar se cliente já possui 3 empregadoras ativas
    @Query("SELECT COUNT(e) >= 3 FROM Empregadora e WHERE e.cliente.id = :clienteId AND e.ativo = true")
    boolean clientePossuiMaximoEmpregadoras(@Param("clienteId") Long clienteId);

    // Buscar empregadoras por rendimento (range)
    @Query("SELECT e FROM Empregadora e WHERE e.rendimento BETWEEN :rendimentoMin AND :rendimentoMax AND e.ativo = true ORDER BY e.rendimento DESC")
    List<Empregadora> findByRendimentoBetween(@Param("rendimentoMin") BigDecimal rendimentoMin, @Param("rendimentoMax") BigDecimal rendimentoMax);

    // Calcular rendimento total do cliente
    @Query("SELECT COALESCE(SUM(e.rendimento), 0) FROM Empregadora e WHERE e.cliente.id = :clienteId AND e.ativo = true")
    BigDecimal calcularRendimentoTotalCliente(@Param("clienteId") Long clienteId);

    // Buscar empregadoras por cargo
    @Query("SELECT e FROM Empregadora e WHERE LOWER(e.cargo) LIKE LOWER(CONCAT('%', :cargo, '%')) AND e.ativo = true ORDER BY e.nome ASC")
    List<Empregadora> findByCargoContaining(@Param("cargo") String cargo);

    // Buscar empregadoras por cidade (usando endereço)
    @Query("SELECT e FROM Empregadora e WHERE LOWER(e.endereco) LIKE LOWER(CONCAT('%', :cidade, '%')) AND e.ativo = true ORDER BY e.nome ASC")
    List<Empregadora> findByCidade(@Param("cidade") String cidade);

    // Buscar empregadoras com telefone
    @Query("SELECT e FROM Empregadora e WHERE e.telefone IS NOT NULL AND e.telefone != '' AND e.ativo = true ORDER BY e.nome ASC")
    List<Empregadora> findEmpregadorasComTelefone();

    // Buscar empregadoras com email
    @Query("SELECT e FROM Empregadora e WHERE e.email IS NOT NULL AND e.email != '' AND e.ativo = true ORDER BY e.nome ASC")
    List<Empregadora> findEmpregadorasComEmail();

    // Buscar empregadoras por data de admissão (range)
    @Query("SELECT e FROM Empregadora e WHERE e.dataAdmissao BETWEEN :dataInicio AND :dataFim AND e.ativo = true ORDER BY e.dataAdmissao DESC")
    List<Empregadora> findByDataAdmissaoBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, @Param("dataFim") java.time.LocalDateTime dataFim);

    // Contar empregadoras por cliente
    @Query("SELECT COUNT(e) FROM Empregadora e WHERE e.cliente.id = :clienteId AND e.ativo = true")
    Long countByClienteId(@Param("clienteId") Long clienteId);

    // Buscar empregadoras com maior rendimento
    @Query("SELECT e FROM Empregadora e WHERE e.ativo = true ORDER BY e.rendimento DESC")
    List<Empregadora> findEmpregadorasComMaiorRendimento();

    // Buscar empregadoras por nome do cliente (usando join)
    @Query("SELECT e FROM Empregadora e JOIN e.cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeCliente, '%')) AND e.ativo = true ORDER BY e.nome ASC")
    List<Empregadora> findByClienteNomeContaining(@Param("nomeCliente") String nomeCliente);
}
