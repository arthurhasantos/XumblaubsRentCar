package com.app.aluguel.repository;

import com.app.aluguel.model.Contrato;
import com.app.aluguel.model.StatusContrato;
import com.app.aluguel.model.Cliente;
import com.app.aluguel.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    
    List<Contrato> findByStatus(StatusContrato status);
    
    List<Contrato> findByCliente(Cliente cliente);
    
    List<Contrato> findByAutomovel(Automovel automovel);
    
    @Query("SELECT c FROM Contrato c WHERE c.cliente = :cliente AND c.status = :status")
    List<Contrato> findByClienteAndStatus(@Param("cliente") Cliente cliente, @Param("status") StatusContrato status);
    
    @Query("SELECT c FROM Contrato c WHERE c.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<Contrato> findByDataInicioBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT c FROM Contrato c WHERE c.status = :status AND c.dataCriacao >= :dataInicio")
    List<Contrato> findByStatusAndDataCriacaoAfter(@Param("status") StatusContrato status, @Param("dataInicio") LocalDate dataInicio);
}
