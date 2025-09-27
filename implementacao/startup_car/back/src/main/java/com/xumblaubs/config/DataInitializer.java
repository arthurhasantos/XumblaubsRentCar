package com.xumblaubs.config;

import com.xumblaubs.entity.Role;
import com.xumblaubs.entity.User;
import com.xumblaubs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        logger.info("🚀 Iniciando criação de dados padrão...");
        
        try {
            createDefaultUsers();
            logger.info("✅ Dados padrão criados com sucesso!");
        } catch (Exception e) {
            logger.error("❌ Erro ao criar dados padrão: {}", e.getMessage(), e);
        }
    }

    private void createDefaultUsers() {
        // Verificar se já existe um usuário admin
        if (!userRepository.existsByEmail("admin@admin.com")) {
            User admin = new User();
            admin.setName("Administrador");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            
            userRepository.save(admin);
            logger.info("👤 Usuário administrador criado: admin@admin.com / admin123");
        } else {
            logger.info("👤 Usuário administrador já existe");
        }
    }
}
