package com.xumblaubs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import java.util.Set;

@Component
@Order(1)
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void run(String... args) throws Exception {
        logger.info("🚀 Iniciando verificação e criação das tabelas do banco de dados...");
        
        try {
            // Verificar conexão com o banco
            checkDatabaseConnection();
            
            // Verificar se as tabelas foram criadas
            checkTablesCreation();
            
            // Listar todas as entidades JPA
            listJpaEntities();
            
            logger.info("✅ Inicialização do banco de dados concluída com sucesso!");
            
        } catch (Exception e) {
            logger.error("❌ Erro durante a inicialização do banco de dados: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void checkDatabaseConnection() {
        try {
            logger.info("🔍 Verificando conexão com o banco de dados...");
            String result = jdbcTemplate.queryForObject("SELECT 'Conexão OK' as status", String.class);
            logger.info("✅ Conexão com banco estabelecida: {}", result);
        } catch (Exception e) {
            logger.error("❌ Erro na conexão com o banco: {}", e.getMessage());
            throw new RuntimeException("Falha na conexão com o banco de dados", e);
        }
    }

    private void checkTablesCreation() {
        try {
            logger.info("🔍 Verificando criação das tabelas...");
            
            // Verificar se a tabela clientes existe
            String clientesExists = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'clientes') THEN 'EXISTS' ELSE 'NOT_EXISTS' END",
                String.class
            );
            
            if ("EXISTS".equals(clientesExists)) {
                logger.info("✅ Tabela 'clientes' criada com sucesso!");
                
                // Contar registros na tabela
                Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM clientes", Integer.class);
                logger.info("📊 Tabela 'clientes' contém {} registros", count);
            } else {
                logger.warn("⚠️ Tabela 'clientes' não foi encontrada!");
            }
            
            // Verificar se a tabela users existe
            String usersExists = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'users') THEN 'EXISTS' ELSE 'NOT_EXISTS' END",
                String.class
            );
            
            if ("EXISTS".equals(usersExists)) {
                logger.info("✅ Tabela 'users' criada com sucesso!");
                
                // Contar registros na tabela
                Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
                logger.info("📊 Tabela 'users' contém {} registros", count);
            } else {
                logger.warn("⚠️ Tabela 'users' não foi encontrada!");
            }
            
        } catch (Exception e) {
            logger.error("❌ Erro ao verificar tabelas: {}", e.getMessage());
            throw new RuntimeException("Falha na verificação das tabelas", e);
        }
    }

    private void listJpaEntities() {
        try {
            logger.info("🔍 Listando entidades JPA mapeadas...");
            Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
            
            logger.info("📋 Entidades JPA encontradas: {}", entities.size());
            for (EntityType<?> entity : entities) {
                logger.info("  - {} (tabela: {})", 
                    entity.getName(), 
                    entity.getJavaType().getSimpleName());
            }
            
        } catch (Exception e) {
            logger.error("❌ Erro ao listar entidades JPA: {}", e.getMessage());
        }
    }
}
