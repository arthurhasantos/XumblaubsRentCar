package com.xumblaubs.config;

import com.xumblaubs.entity.Banco;
import com.xumblaubs.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BancoDataInitializer implements CommandLineRunner {

    @Autowired
    private BancoRepository bancoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar se já existem bancos cadastrados
        if (bancoRepository.count() == 0) {
            System.out.println("🏦 Inicializando dados dos bancos...");

            // Banco Inter
            Banco bancoInter = new Banco();
            bancoInter.setNome("Banco Inter");
            bancoInter.setCodigo("077");
            bancoInter.setCnpj("00.416.968/0001-01");
            bancoInter.setEndereco("Av. Barbacena, 1219 - Santo Agostinho, Belo Horizonte - MG");
            bancoInter.setEmail("contato@bancointer.com.br");
            bancoInter.setTelefone("(31) 3003-4077");
            bancoInter.setTaxaJurosPadrao(new BigDecimal("0.025")); // 2.5% ao mês
            bancoInter.setLimiteCreditoMaximo(new BigDecimal("50000.00"));
            bancoInter.setAtivo(true);
            bancoRepository.save(bancoInter);
            System.out.println("✅ Banco Inter criado com sucesso!");

            // Banco Itaú
            Banco bancoItau = new Banco();
            bancoItau.setNome("Banco Itaú");
            bancoItau.setCodigo("341");
            bancoItau.setCnpj("60.701.190/0001-04");
            bancoItau.setEndereco("Av. Paulista, 1000 - Bela Vista, São Paulo - SP");
            bancoItau.setEmail("contato@itau.com.br");
            bancoItau.setTelefone("(11) 3003-3030");
            bancoItau.setTaxaJurosPadrao(new BigDecimal("0.035")); // 3.5% ao mês
            bancoItau.setLimiteCreditoMaximo(new BigDecimal("100000.00"));
            bancoItau.setAtivo(true);
            bancoRepository.save(bancoItau);
            System.out.println("✅ Banco Itaú criado com sucesso!");

            // Banco do Brasil
            Banco bancoBdB = new Banco();
            bancoBdB.setNome("Banco do Brasil");
            bancoBdB.setCodigo("001");
            bancoBdB.setCnpj("00.000.000/0001-91");
            bancoBdB.setEndereco("SBS Quadra 1, Bloco A - Brasília - DF");
            bancoBdB.setEmail("contato@bb.com.br");
            bancoBdB.setTelefone("(61) 3003-0001");
            bancoBdB.setTaxaJurosPadrao(new BigDecimal("0.030")); // 3.0% ao mês
            bancoBdB.setLimiteCreditoMaximo(new BigDecimal("75000.00"));
            bancoBdB.setAtivo(true);
            bancoRepository.save(bancoBdB);
            System.out.println("✅ Banco do Brasil criado com sucesso!");

            System.out.println("🎉 Todos os bancos foram inicializados com sucesso!");
        } else {
            System.out.println("ℹ️  Bancos já existem no banco de dados. Pulando inicialização.");
        }
    }
}
