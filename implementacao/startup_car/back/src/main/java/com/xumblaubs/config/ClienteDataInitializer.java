package com.xumblaubs.config;

import com.xumblaubs.entity.Cliente;
import com.xumblaubs.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ClienteDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDataInitializer.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("🚀 Inicializando dados de clientes...");
        createDefaultClientes();
    }

    private void createDefaultClientes() {
        // Cliente 1: Lucas Jácome
        if (!clienteRepository.existsByCpf("123.456.789-01")) {
            Cliente lucas = new Cliente();
            lucas.setRg("12.345.678-9");
            lucas.setCpf("123.456.789-01");
            lucas.setNome("Lucas Jácome");
            lucas.setEndereco("Rua das Flores, 123 - Centro, São Paulo - SP");
            lucas.setProfissao("Desenvolvedor de Software");
            lucas.setEmail("lucas@exemplo.com");
            lucas.setSenha("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi"); // senha: 123456
            lucas.setAtivo(true);
            
            clienteRepository.save(lucas);
            logger.info("👤 Cliente criado: Lucas Jácome (CPF: 123.456.789-01)");
        } else {
            logger.info("👤 Cliente Lucas Jácome já existe");
        }

        // Cliente 2: Victor Ferreira
        if (!clienteRepository.existsByCpf("987.654.321-02")) {
            Cliente victor = new Cliente();
            victor.setRg("98.765.432-1");
            victor.setCpf("987.654.321-02");
            victor.setNome("Victor Ferreira");
            victor.setEndereco("Av. Paulista, 456 - Bela Vista, São Paulo - SP");
            victor.setProfissao("Analista de Sistemas");
            victor.setEmail("victor@exemplo.com");
            victor.setSenha("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi"); // senha: 123456
            victor.setAtivo(true);
            
            clienteRepository.save(victor);
            logger.info("👤 Cliente criado: Victor Ferreira (CPF: 987.654.321-02)");
        } else {
            logger.info("👤 Cliente Victor Ferreira já existe");
        }

        // Cliente 3: Arthur Henrique
        if (!clienteRepository.existsByCpf("456.789.123-03")) {
            Cliente arthur = new Cliente();
            arthur.setRg("45.678.912-3");
            arthur.setCpf("456.789.123-03");
            arthur.setNome("Arthur Henrique");
            arthur.setEndereco("Rua Augusta, 789 - Consolação, São Paulo - SP");
            arthur.setProfissao("Engenheiro de Software");
            arthur.setEmail("arthur@exemplo.com");
            arthur.setSenha("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi"); // senha: 123456
            arthur.setAtivo(true);
            
            clienteRepository.save(arthur);
            logger.info("👤 Cliente criado: Arthur Henrique (CPF: 456.789.123-03)");
        } else {
            logger.info("👤 Cliente Arthur Henrique já existe");
        }

        logger.info("✅ Inicialização de clientes concluída!");
    }
}
