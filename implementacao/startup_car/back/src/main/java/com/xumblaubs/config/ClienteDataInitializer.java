package com.xumblaubs.config;

import com.xumblaubs.dto.ClienteRequest;
import com.xumblaubs.service.ClienteService;
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
    
    @Autowired
    private ClienteService clienteService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("🚀 Inicializando dados de clientes...");
        createDefaultClientes();
    }

    private void createDefaultClientes() {
        // Cliente 1: Lucas Jácome
        if (!clienteRepository.existsByCpf("123.456.789-01")) {
            try {
                ClienteRequest lucasRequest = new ClienteRequest();
                lucasRequest.setRg("12.345.678-9");
                lucasRequest.setCpf("123.456.789-01");
                lucasRequest.setNome("Lucas Jácome");
                lucasRequest.setEndereco("Rua das Flores, 123 - Centro, São Paulo - SP");
                lucasRequest.setProfissao("Desenvolvedor de Software");
                lucasRequest.setEmail("lucas@exemplo.com");
                lucasRequest.setSenha("123456");
                
                clienteService.create(lucasRequest);
                logger.info("👤 Cliente criado: Lucas Jácome (CPF: 123.456.789-01)");
            } catch (Exception e) {
                logger.error("❌ Erro ao criar cliente Lucas: {}", e.getMessage());
            }
        } else {
            logger.info("👤 Cliente Lucas Jácome já existe");
        }

        // Cliente 2: Victor Ferreira
        if (!clienteRepository.existsByCpf("987.654.321-02")) {
            try {
                ClienteRequest victorRequest = new ClienteRequest();
                victorRequest.setRg("98.765.432-1");
                victorRequest.setCpf("987.654.321-02");
                victorRequest.setNome("Victor Ferreira");
                victorRequest.setEndereco("Av. Paulista, 456 - Bela Vista, São Paulo - SP");
                victorRequest.setProfissao("Analista de Sistemas");
                victorRequest.setEmail("victor@exemplo.com");
                victorRequest.setSenha("123456");
                
                clienteService.create(victorRequest);
                logger.info("👤 Cliente criado: Victor Ferreira (CPF: 987.654.321-02)");
            } catch (Exception e) {
                logger.error("❌ Erro ao criar cliente Victor: {}", e.getMessage());
            }
        } else {
            logger.info("👤 Cliente Victor Ferreira já existe");
        }

        // Cliente 3: Arthur Henrique
        if (!clienteRepository.existsByCpf("456.789.123-03")) {
            try {
                ClienteRequest arthurRequest = new ClienteRequest();
                arthurRequest.setRg("45.678.912-3");
                arthurRequest.setCpf("456.789.123-03");
                arthurRequest.setNome("Arthur Henrique");
                arthurRequest.setEndereco("Rua Augusta, 789 - Consolação, São Paulo - SP");
                arthurRequest.setProfissao("Engenheiro de Software");
                arthurRequest.setEmail("arthur@exemplo.com");
                arthurRequest.setSenha("123456");
                
                clienteService.create(arthurRequest);
                logger.info("👤 Cliente criado: Arthur Henrique (CPF: 456.789.123-03)");
            } catch (Exception e) {
                logger.error("❌ Erro ao criar cliente Arthur: {}", e.getMessage());
            }
        } else {
            logger.info("👤 Cliente Arthur Henrique já existe");
        }

        logger.info("✅ Inicialização de clientes concluída!");
    }
}
