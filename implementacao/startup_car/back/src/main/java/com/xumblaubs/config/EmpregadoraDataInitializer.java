package com.xumblaubs.config;

import com.xumblaubs.entity.Cliente;
import com.xumblaubs.dto.EmpregadoraRequest;
import com.xumblaubs.service.EmpregadoraService;
import com.xumblaubs.repository.ClienteRepository;
import com.xumblaubs.repository.EmpregadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(3)
public class EmpregadoraDataInitializer implements CommandLineRunner {
    
    public EmpregadoraDataInitializer() {
        System.out.println("🚀 EmpregadoraDataInitializer CONSTRUTOR executado!");
    }

    @Autowired
    private EmpregadoraRepository empregadoraRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EmpregadoraService empregadoraService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔍 EmpregadoraDataInitializer iniciado...");
        System.out.println("📊 Total de empregadoras existentes: " + empregadoraRepository.count());
        
        // Verificar se já existem empregadoras cadastradas
        if (empregadoraRepository.count() == 0) {
            System.out.println("🏢 Inicializando dados das empregadoras...");

            // Buscar todos os clientes
            var clientes = clienteRepository.findAll();
            System.out.println("📋 Total de clientes encontrados: " + clientes.size());
            
            if (!clientes.isEmpty()) {
                // Criar empregadoras para o primeiro cliente
                Cliente cliente = clientes.get(0);
                System.out.println("📋 Cliente encontrado: " + cliente.getNome() + " (ID: " + cliente.getId() + ")");
                
                // Empregadora Localiza - versão simplificada
                try {
                    EmpregadoraRequest localizaRequest = new EmpregadoraRequest();
                    localizaRequest.setClienteId(cliente.getId());
                    localizaRequest.setNome("Localiza Rent a Car");
                    localizaRequest.setRendimento(new BigDecimal("3500.00"));
                    localizaRequest.setEmail("admin@localiza.com.br");
                    localizaRequest.setSenha("123456");
                    
                    empregadoraService.criarEmpregadora(localizaRequest);
                    System.out.println("✅ Empregadora Localiza criada com sucesso para cliente: " + cliente.getNome());
                } catch (Exception e) {
                    System.err.println("❌ Erro ao criar empregadora Localiza: " + e.getMessage());
                    e.printStackTrace();
                }

                // Empregadora Unidas - versão simplificada
                try {
                    EmpregadoraRequest unidasRequest = new EmpregadoraRequest();
                    unidasRequest.setClienteId(cliente.getId());
                    unidasRequest.setNome("Unidas Locadora");
                    unidasRequest.setRendimento(new BigDecimal("4200.00"));
                    unidasRequest.setEmail("admin@unidas.com.br");
                    unidasRequest.setSenha("123456");
                    
                    empregadoraService.criarEmpregadora(unidasRequest);
                    System.out.println("✅ Empregadora Unidas criada com sucesso para cliente: " + cliente.getNome());
                } catch (Exception e) {
                    System.err.println("❌ Erro ao criar empregadora Unidas: " + e.getMessage());
                    e.printStackTrace();
                }

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
