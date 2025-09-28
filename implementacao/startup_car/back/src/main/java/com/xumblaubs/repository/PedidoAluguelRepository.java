package com.xumblaubs.repository;

import com.xumblaubs.entity.PedidoAluguel;
import com.xumblaubs.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Long> {

    // Buscar pedidos por cliente
    List<PedidoAluguel> findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(Long clienteId);
    
    // Buscar pedidos por status
    List<PedidoAluguel> findByStatusAndAtivoTrueOrderByCreatedAtDesc(StatusPedido status);
    
    // Buscar pedidos por automóvel
    List<PedidoAluguel> findByAutomovelIdAndAtivoTrueOrderByCreatedAtDesc(Long automovelId);
    
    // Buscar pedidos ativos
    List<PedidoAluguel> findByAtivoTrueOrderByCreatedAtDesc();
    
    // Buscar pedidos inativos
    List<PedidoAluguel> findByAtivoFalseOrderByCreatedAtDesc();

    // Buscar pedidos com filtros
    @Query("SELECT p FROM PedidoAluguel p WHERE " +
           "(:clienteId IS NULL OR p.cliente.id = :clienteId) AND " +
           "(:automovelId IS NULL OR p.automovel.id = :automovelId) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:dataInicio IS NULL OR p.dataInicio >= :dataInicio) AND " +
           "(:dataFim IS NULL OR p.dataFim <= :dataFim) AND " +
           "(:ativo IS NULL OR p.ativo = :ativo) " +
           "ORDER BY p.createdAt DESC")
    List<PedidoAluguel> findByFiltros(@Param("clienteId") Long clienteId,
                                     @Param("automovelId") Long automovelId,
                                     @Param("status") StatusPedido status,
                                     @Param("dataInicio") LocalDateTime dataInicio,
                                     @Param("dataFim") LocalDateTime dataFim,
                                     @Param("ativo") Boolean ativo);

    // Verificar se existe pedido ativo para o automóvel no período
    @Query("SELECT COUNT(p) > 0 FROM PedidoAluguel p WHERE " +
           "p.automovel.id = :automovelId AND " +
           "p.ativo = true AND " +
           "p.status IN ('APROVADO', 'EM_ANDAMENTO') AND " +
           "((p.dataInicio <= :dataInicio AND p.dataFim > :dataInicio) OR " +
           "(p.dataInicio < :dataFim AND p.dataFim >= :dataFim) OR " +
           "(p.dataInicio >= :dataInicio AND p.dataFim <= :dataFim))")
    boolean existsPedidoAtivoNoPeriodo(@Param("automovelId") Long automovelId,
                                      @Param("dataInicio") LocalDateTime dataInicio,
                                      @Param("dataFim") LocalDateTime dataFim);

    // Verificar se existe pedido ativo para o automóvel no período (excluindo um pedido específico)
    @Query("SELECT COUNT(p) > 0 FROM PedidoAluguel p WHERE " +
           "p.automovel.id = :automovelId AND " +
           "p.ativo = true AND " +
           "p.id != :pedidoId AND " +
           "p.status IN ('APROVADO', 'EM_ANDAMENTO') AND " +
           "((p.dataInicio <= :dataInicio AND p.dataFim > :dataInicio) OR " +
           "(p.dataInicio < :dataFim AND p.dataFim >= :dataFim) OR " +
           "(p.dataInicio >= :dataInicio AND p.dataFim <= :dataFim))")
    boolean existsPedidoAtivoNoPeriodoExcluindoPedido(@Param("automovelId") Long automovelId,
                                                     @Param("dataInicio") LocalDateTime dataInicio,
                                                     @Param("dataFim") LocalDateTime dataFim,
                                                     @Param("pedidoId") Long pedidoId);

    // Contar pedidos por status
    @Query("SELECT p.status, COUNT(p) FROM PedidoAluguel p WHERE p.ativo = true GROUP BY p.status")
    List<Object[]> countByStatus();

    // Buscar pedidos pendentes (para administradores)
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'PENDENTE' AND p.ativo = true ORDER BY p.createdAt ASC")
    List<PedidoAluguel> findPedidosPendentes();

    // Buscar pedidos do cliente com status específico
    @Query("SELECT p FROM PedidoAluguel p WHERE p.cliente.id = :clienteId AND p.status = :status AND p.ativo = true ORDER BY p.createdAt DESC")
    List<PedidoAluguel> findByClienteIdAndStatus(@Param("clienteId") Long clienteId, @Param("status") StatusPedido status);

    // Verificar se cliente tem pedido ativo
    @Query("SELECT COUNT(p) > 0 FROM PedidoAluguel p WHERE " +
           "p.cliente.id = :clienteId AND " +
           "p.ativo = true AND " +
           "p.status IN ('APROVADO', 'EM_ANDAMENTO')")
    boolean existsPedidoAtivoDoCliente(@Param("clienteId") Long clienteId);

    // Buscar pedidos que precisam ser finalizados (data fim passou)
    @Query("SELECT p FROM PedidoAluguel p WHERE " +
           "p.status = 'EM_ANDAMENTO' AND " +
           "p.dataFim < :agora AND " +
           "p.ativo = true")
    List<PedidoAluguel> findPedidosParaFinalizar(@Param("agora") LocalDateTime agora);
}
