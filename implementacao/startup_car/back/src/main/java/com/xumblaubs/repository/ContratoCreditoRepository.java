package com.xumblaubs.repository;

import com.xumblaubs.entity.ContratoCredito;
import com.xumblaubs.entity.StatusContratoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoCreditoRepository extends JpaRepository<ContratoCredito, Long> {

    // Buscar contratos ativos
    List<ContratoCredito> findByAtivoTrueOrderByCreatedAtDesc();

    // Buscar contratos por cliente
    List<ContratoCredito> findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(Long clienteId);

    // Buscar contratos por banco
    List<ContratoCredito> findByBancoIdAndAtivoTrueOrderByCreatedAtDesc(Long bancoId);

    // Buscar contratos por status
    List<ContratoCredito> findByStatusAndAtivoTrueOrderByCreatedAtDesc(StatusContratoCredito status);

    // Buscar contrato por número
    Optional<ContratoCredito> findByNumeroAndAtivoTrue(String numero);

    // Buscar contratos pendentes
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'PENDENTE' AND c.ativo = true ORDER BY c.createdAt DESC")
    List<ContratoCredito> findContratosPendentes();

    // Buscar contratos ativos por cliente
    @Query("SELECT c FROM ContratoCredito c WHERE c.cliente.id = :clienteId AND c.status = 'ATIVO' AND c.ativo = true")
    List<ContratoCredito> findContratosAtivosPorCliente(@Param("clienteId") Long clienteId);

    // Verificar se cliente já possui contrato ativo
    @Query("SELECT COUNT(c) > 0 FROM ContratoCredito c WHERE c.cliente.id = :clienteId AND c.status IN ('ATIVO', 'PENDENTE') AND c.ativo = true")
    boolean existsContratoAtivoDoCliente(@Param("clienteId") Long clienteId);

    // Buscar contratos por valor total (range)
    @Query("SELECT c FROM ContratoCredito c WHERE c.valorTotal BETWEEN :valorMin AND :valorMax AND c.ativo = true ORDER BY c.valorTotal DESC")
    List<ContratoCredito> findByValorTotalBetween(@Param("valorMin") Double valorMin, @Param("valorMax") Double valorMax);

    // Buscar contratos por prazo
    @Query("SELECT c FROM ContratoCredito c WHERE c.prazoMeses BETWEEN :prazoMin AND :prazoMax AND c.ativo = true ORDER BY c.prazoMeses DESC")
    List<ContratoCredito> findByPrazoMesesBetween(@Param("prazoMin") Integer prazoMin, @Param("prazoMax") Integer prazoMax);

    // Contar contratos por status
    @Query("SELECT COUNT(c) FROM ContratoCredito c WHERE c.status = :status AND c.ativo = true")
    Long countByStatus(@Param("status") StatusContratoCredito status);

    // Buscar contratos vencidos
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'ATIVO' AND c.ativo = true AND c.createdAt < :dataLimite")
    List<ContratoCredito> findContratosVencidos(@Param("dataLimite") java.time.LocalDateTime dataLimite);

    // Buscar por nome do cliente (usando join)
    @Query("SELECT c FROM ContratoCredito c JOIN c.cliente cl WHERE LOWER(cl.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND c.ativo = true ORDER BY c.createdAt DESC")
    List<ContratoCredito> findByClienteNomeContaining(@Param("nome") String nome);

    // Buscar por nome do banco (usando join)
    @Query("SELECT c FROM ContratoCredito c JOIN c.banco b WHERE LOWER(b.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND c.ativo = true ORDER BY c.createdAt DESC")
    List<ContratoCredito> findByBancoNomeContaining(@Param("nome") String nome);
}
