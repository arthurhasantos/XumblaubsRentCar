package com.app.aluguel.repository;

import com.app.aluguel.model.PedidoAluguel;
import com.app.aluguel.model.StatusPedido;
import com.app.aluguel.model.Cliente;
import com.app.aluguel.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Long> {
    
    List<PedidoAluguel> findByStatus(StatusPedido status);
    
    List<PedidoAluguel> findByCliente(Cliente cliente);
    
    List<PedidoAluguel> findByAgenteAnalisador(Agente agente);
    
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = :status AND p.dataCriacao >= :dataInicio")
    List<PedidoAluguel> findByStatusAndDataCriacaoAfter(@Param("status") StatusPedido status, @Param("dataInicio") LocalDate dataInicio);
    
    @Query("SELECT p FROM PedidoAluguel p WHERE p.cliente = :cliente AND p.status = :status")
    List<PedidoAluguel> findByClienteAndStatus(@Param("cliente") Cliente cliente, @Param("status") StatusPedido status);
    
    @Query("SELECT p FROM PedidoAluguel p WHERE p.agenteAnalisador = :agente AND p.status = :status")
    List<PedidoAluguel> findByAgenteAndStatus(@Param("agente") Agente agente, @Param("status") StatusPedido status);
    
    @Query("SELECT p FROM PedidoAluguel p WHERE p.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<PedidoAluguel> findByDataInicioBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
