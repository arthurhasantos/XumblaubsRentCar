package com.xumblaubs.config;

import com.xumblaubs.dto.BancoRequest;
import com.xumblaubs.service.BancoService;
import com.xumblaubs.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class BancoDataInitializer implements CommandLineRunner {
    
    public BancoDataInitializer() {
        System.out.println("🚀 BancoDataInitializer CONSTRUTOR executado!");
    }

    @Autowired
    private BancoRepository bancoRepository;
    
    @Autowired
    private BancoService bancoService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔍 BancoDataInitializer iniciado...");
        System.out.println("📊 Total de bancos existentes: " + bancoRepository.count());
        
        // Verificar se já existem bancos cadastrados
        if (bancoRepository.count() == 0) {
            System.out.println("🏦 Inicializando dados dos bancos...");

            // Banco Inter - versão simplificada
            try {
                BancoRequest interRequest = new BancoRequest();
                interRequest.setNome("Banco Inter");
                interRequest.setCodigo("077");
                interRequest.setCnpj("00.416.968/0001-01");
                interRequest.setEmail("admin@bancointer.com.br");
                interRequest.setSenha("123456");
                
                bancoService.criarBanco(interRequest);
                System.out.println("✅ Banco Inter criado com sucesso!");
            } catch (Exception e) {
                System.err.println("❌ Erro ao criar Banco Inter: " + e.getMessage());
                e.printStackTrace();
            }

            // Banco Itaú - versão simplificada
            try {
                BancoRequest itauRequest = new BancoRequest();
                itauRequest.setNome("Banco Itaú");
                itauRequest.setCodigo("341");
                itauRequest.setCnpj("60.701.190/0001-04");
                itauRequest.setEmail("admin@itau.com.br");
                itauRequest.setSenha("123456");
                
                bancoService.criarBanco(itauRequest);
                System.out.println("✅ Banco Itaú criado com sucesso!");
            } catch (Exception e) {
                System.err.println("❌ Erro ao criar Banco Itaú: " + e.getMessage());
                e.printStackTrace();
            }

            // Banco do Brasil - versão simplificada
            try {
                BancoRequest bbRequest = new BancoRequest();
                bbRequest.setNome("Banco do Brasil");
                bbRequest.setCodigo("001");
                bbRequest.setCnpj("00.000.000/0001-91");
                bbRequest.setEmail("admin@bb.com.br");
                bbRequest.setSenha("123456");
                
                bancoService.criarBanco(bbRequest);
                System.out.println("✅ Banco do Brasil criado com sucesso!");
            } catch (Exception e) {
                System.err.println("❌ Erro ao criar Banco do Brasil: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("🎉 Todos os bancos foram inicializados com sucesso!");
        } else {
            System.out.println("ℹ️  Bancos já existem no banco de dados. Pulando inicialização.");
        }
    }
}
