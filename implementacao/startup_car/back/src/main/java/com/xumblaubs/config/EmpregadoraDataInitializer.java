package com.xumblaubs.config;

import com.xumblaubs.entity.Cliente;
import com.xumblaubs.entity.Empregadora;
import com.xumblaubs.repository.ClienteRepository;
import com.xumblaubs.repository.EmpregadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class EmpregadoraDataInitializer implements CommandLineRunner {

    @Autowired
    private EmpregadoraRepository empregadoraRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar se já existem empregadoras cadastradas
        if (empregadoraRepository.count() == 0) {
            System.out.println("🏢 Inicializando dados das empregadoras...");

            // Buscar um cliente para associar as empregadoras
            Cliente cliente = clienteRepository.findAll().stream().findFirst().orElse(null);
            
            if (cliente != null) {
                // Empregadora Localiza
                Empregadora localiza = new Empregadora();
                localiza.setCliente(cliente);
                localiza.setNome("Localiza Rent a Car");
                localiza.setRendimento(new BigDecimal("3500.00"));
                localiza.setCargo("Analista de Sistemas");
                localiza.setCnpj("33.592.510/0001-54");
                localiza.setEndereco("Av. Raja Gabaglia, 2000 - Lourdes, Belo Horizonte - MG");
                localiza.setEmail("rh@localiza.com");
                localiza.setTelefone("(31) 3290-5000");
                localiza.setDataAdmissao(LocalDateTime.now().minusMonths(12));
                localiza.setAtivo(true);
                empregadoraRepository.save(localiza);
                System.out.println("✅ Empregadora Localiza criada com sucesso!");

                // Empregadora Unidas
                Empregadora unidas = new Empregadora();
                unidas.setCliente(cliente);
                unidas.setNome("Unidas Locadora");
                unidas.setRendimento(new BigDecimal("4200.00"));
                unidas.setCargo("Desenvolvedor Full Stack");
                unidas.setCnpj("17.167.412/0001-13");
                unidas.setEndereco("Rua da Consolação, 3.000 - Consolação, São Paulo - SP");
                unidas.setEmail("recrutamento@unidas.com.br");
                unidas.setTelefone("(11) 3003-3030");
                unidas.setDataAdmissao(LocalDateTime.now().minusMonths(8));
                unidas.setAtivo(true);
                empregadoraRepository.save(unidas);
                System.out.println("✅ Empregadora Unidas criada com sucesso!");

                System.out.println("🎉 Todas as empregadoras foram inicializadas com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum cliente encontrado. Empregadoras não serão criadas.");
                System.out.println("💡 Certifique-se de que existem clientes cadastrados antes de criar empregadoras.");
            }
        } else {
            System.out.println("ℹ️  Empregadoras já existem no banco de dados. Pulando inicialização.");
        }
    }
}
