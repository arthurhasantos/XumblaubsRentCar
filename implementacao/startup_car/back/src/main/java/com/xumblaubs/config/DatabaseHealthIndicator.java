package com.xumblaubs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthIndicator.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            logger.debug("🔍 Verificando saúde do banco de dados...");
            
            // Verificar conexão
            String connectionStatus = jdbcTemplate.queryForObject("SELECT 'OK' as status", String.class);
            
            // Verificar tabelas
            Map<String, Object> tableInfo = new HashMap<>();
            
            // Verificar tabela clientes
            try {
                Integer clientesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM clientes", Integer.class);
                tableInfo.put("clientes_count", clientesCount);
                tableInfo.put("clientes_status", "OK");
            } catch (Exception e) {
                tableInfo.put("clientes_status", "ERROR: " + e.getMessage());
                logger.warn("⚠️ Problema na tabela clientes: {}", e.getMessage());
            }
            
            // Verificar tabela users
            try {
                Integer usersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
                tableInfo.put("users_count", usersCount);
                tableInfo.put("users_status", "OK");
            } catch (Exception e) {
                tableInfo.put("users_status", "ERROR: " + e.getMessage());
                logger.warn("⚠️ Problema na tabela users: {}", e.getMessage());
            }
            
            // Verificar versão do PostgreSQL
            String version = jdbcTemplate.queryForObject("SELECT version()", String.class);
            tableInfo.put("database_version", version);
            
            logger.info("✅ Saúde do banco verificada com sucesso");
            
            return Health.up()
                    .withDetail("connection", connectionStatus)
                    .withDetail("tables", tableInfo)
                    .build();
                    
        } catch (Exception e) {
            logger.error("❌ Erro na verificação de saúde do banco: {}", e.getMessage());
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
