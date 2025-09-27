package com.xumblaubs.config;

import com.xumblaubs.entity.Automovel;
import com.xumblaubs.entity.TipoProprietario;
import com.xumblaubs.repository.AutomovelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class AutomovelDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AutomovelDataInitializer.class);

    @Autowired
    private AutomovelRepository automovelRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("🚗 Iniciando criação de automóveis populares...");
        
        try {
            createPopularCars();
            logger.info("✅ Automóveis populares criados com sucesso!");
        } catch (Exception e) {
            logger.error("❌ Erro ao criar automóveis populares: {}", e.getMessage(), e);
        }
    }

    private void createPopularCars() {
        // Verificar se já existem automóveis
        if (automovelRepository.count() > 0) {
            logger.info("🚗 Automóveis já existem no banco de dados");
            return;
        }

        // Carros populares brasileiros
        createCar("MAT001", 2023, "Volkswagen", "Golf", "ABC1234", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT002", 2022, "Toyota", "Corolla", "DEF5678", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT003", 2023, "Honda", "Civic", "GHI9012", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT004", 2022, "Chevrolet", "Onix", "JKL3456", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT005", 2023, "Fiat", "Argo", "MNO7890", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        
        // Carros de luxo
        createCar("MAT006", 2023, "BMW", "320i", "PQR1234", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT007", 2022, "Mercedes-Benz", "C200", "STU5678", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT008", 2023, "Audi", "A4", "VWX9012", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        
        // SUVs populares
        createCar("MAT009", 2022, "Toyota", "RAV4", "YZA3456", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT010", 2023, "Honda", "CR-V", "BCD7890", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT011", 2022, "Volkswagen", "Tiguan", "EFG1234", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        
        // Carros econômicos
        createCar("MAT012", 2023, "Renault", "Kwid", "HIJ5678", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT013", 2022, "Nissan", "March", "KLM9012", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        createCar("MAT014", 2023, "Hyundai", "HB20", "NOP3456", TipoProprietario.EMPRESA, "Empresa Aluguel Car");
        
        // Carros de bancos (financiados)
        createCar("MAT015", 2021, "Ford", "Ka", "QRS7890", TipoProprietario.BANCO, "Banco do Brasil");
        createCar("MAT016", 2022, "Volkswagen", "Polo", "TUV1234", TipoProprietario.BANCO, "Itaú");
        createCar("MAT017", 2021, "Chevrolet", "Prisma", "WXY5678", TipoProprietario.BANCO, "Bradesco");
        
        // Carros de clientes particulares
        createCar("MAT018", 2020, "Fiat", "Uno", "ZAB9012", TipoProprietario.CLIENTE, "João Silva");
        createCar("MAT019", 2019, "Volkswagen", "Gol", "CDE3456", TipoProprietario.CLIENTE, "Maria Santos");
        createCar("MAT020", 2021, "Chevrolet", "Celta", "FGH7890", TipoProprietario.CLIENTE, "Pedro Costa");
        
        logger.info("🚗 {} automóveis populares criados", 20);
    }

    private void createCar(String matricula, Integer ano, String marca, String modelo, 
                          String placa, TipoProprietario tipoProprietario, String proprietarioNome) {
        try {
            Automovel automovel = new Automovel();
            automovel.setMatricula(matricula);
            automovel.setAno(ano);
            automovel.setMarca(marca);
            automovel.setModelo(modelo);
            automovel.setPlaca(placa);
            automovel.setTipoProprietario(tipoProprietario);
            automovel.setProprietarioNome(proprietarioNome);
            automovel.setAtivo(true);
            
            // Adicionar observações baseadas no tipo
            switch (tipoProprietario) {
                case EMPRESA:
                    automovel.setObservacoes("Veículo da frota empresarial - Manutenção em dia");
                    break;
                case BANCO:
                    automovel.setObservacoes("Veículo financiado - Seguro completo");
                    break;
                case CLIENTE:
                    automovel.setObservacoes("Veículo particular - Documentação regular");
                    break;
            }
            
            automovelRepository.save(automovel);
            logger.info("🚗 {} {} {} - {} criado", marca, modelo, ano, placa);
            
        } catch (Exception e) {
            logger.error("❌ Erro ao criar automóvel {} {}: {}", marca, modelo, e.getMessage());
        }
    }
}
