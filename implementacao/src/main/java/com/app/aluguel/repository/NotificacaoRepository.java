package com.app.aluguel.repository;

import com.app.aluguel.model.Notificacao;
import com.app.aluguel.model.Usuario;
import com.app.aluguel.model.TipoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    
    List<Notificacao> findByUsuario(Usuario usuario);
    
    List<Notificacao> findByUsuarioAndLidaFalse(Usuario usuario);
    
    List<Notificacao> findByUsuarioAndAtivaTrue(Usuario usuario);
    
    List<Notificacao> findByTipo(TipoNotificacao tipo);
    
    @Query("SELECT n FROM Notificacao n WHERE n.usuario = :usuario AND n.ativa = true ORDER BY n.dataCriacao DESC")
    List<Notificacao> findAtivasByUsuarioOrderByDataCriacaoDesc(@Param("usuario") Usuario usuario);
    
    @Query("SELECT n FROM Notificacao n WHERE n.usuario = :usuario AND n.lida = false AND n.ativa = true ORDER BY n.dataCriacao DESC")
    List<Notificacao> findNaoLidasByUsuarioOrderByDataCriacaoDesc(@Param("usuario") Usuario usuario);
    
    @Query("SELECT COUNT(n) FROM Notificacao n WHERE n.usuario = :usuario AND n.lida = false AND n.ativa = true")
    Long countNaoLidasByUsuario(@Param("usuario") Usuario usuario);
    
    @Query("SELECT n FROM Notificacao n WHERE n.dataCriacao >= :dataInicio AND n.ativa = true ORDER BY n.dataCriacao DESC")
    List<Notificacao> findAtivasByDataCriacaoAfter(@Param("dataInicio") LocalDateTime dataInicio);
}
