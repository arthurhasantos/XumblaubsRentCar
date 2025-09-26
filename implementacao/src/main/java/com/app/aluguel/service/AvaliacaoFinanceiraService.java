package com.app.aluguel.service;

import com.app.aluguel.model.*;
import com.app.aluguel.repository.PedidoAluguelRepository;
import com.app.aluguel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AvaliacaoFinanceiraService {
    
    @Autowired
    private PedidoAluguelRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    // Configurações para análise financeira
    private static final double RENDA_MINIMA_MULTIPLICADOR = 0.3; // 30% da renda
    private static final double SCORE_MINIMO_APROVACAO = 60.0;
    private static final double SCORE_MAXIMO = 100.0;
    
    /**
     * Realiza análise financeira completa de um pedido
     */
    public Map<String, Object> analisarPedido(Long pedidoId) {
        PedidoAluguel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Cliente cliente = pedido.getCliente();
        
        // Calcular renda total do cliente
        double rendaTotal = cliente.getRendaTotal();
        
        // Calcular valor mensal do aluguel
        long dias = java.time.temporal.ChronoUnit.DAYS.between(pedido.getDataInicio(), pedido.getDataFim());
        double valorMensal = (pedido.getValorTotal() / dias) * 30; // Projeção mensal
        
        // Calcular score de aprovação
        double score = calcularScoreAprovacao(cliente, rendaTotal, valorMensal, pedido.getValorTotal());
        
        // Determinar recomendação
        String recomendacao = determinarRecomendacao(score, rendaTotal, valorMensal);
        
        // Gerar parecer detalhado
        String parecer = gerarParecerDetalhado(cliente, rendaTotal, valorMensal, score, recomendacao);
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("score", score);
        resultado.put("recomendacao", recomendacao);
        resultado.put("parecer", parecer);
        resultado.put("rendaTotal", rendaTotal);
        resultado.put("valorMensal", valorMensal);
        resultado.put("aprovado", score >= SCORE_MINIMO_APROVACAO);
        
        return resultado;
    }
    
    /**
     * Calcula o score de aprovação baseado em múltiplos fatores
     */
    private double calcularScoreAprovacao(Cliente cliente, double rendaTotal, double valorMensal, double valorTotal) {
        double score = 0.0;
        
        // 1. Análise de renda (40% do score)
        double percentualRenda = (valorMensal / rendaTotal) * 100;
        if (percentualRenda <= 20) {
            score += 40; // Excelente
        } else if (percentualRenda <= 30) {
            score += 30; // Bom
        } else if (percentualRenda <= 40) {
            score += 20; // Regular
        } else {
            score += 5; // Ruim
        }
        
        // 2. Análise de estabilidade profissional (30% do score)
        int numeroEmpregos = cliente.getEmpregosRendas().size();
        if (numeroEmpregos >= 2) {
            score += 30; // Múltiplas fontes de renda
        } else if (numeroEmpregos == 1) {
            score += 20; // Fonte única
        } else {
            score += 0; // Sem renda declarada
        }
        
        // 3. Análise de valor do aluguel (20% do score)
        if (valorTotal <= 5000) {
            score += 20; // Valor baixo
        } else if (valorTotal <= 10000) {
            score += 15; // Valor médio
        } else if (valorTotal <= 20000) {
            score += 10; // Valor alto
        } else {
            score += 5; // Valor muito alto
        }
        
        // 4. Análise de dados pessoais (10% do score)
        if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
            score += 5; // Contato disponível
        }
        if (cliente.getTelefone() != null && !cliente.getTelefone().isEmpty()) {
            score += 5; // Telefone disponível
        }
        
        return Math.min(score, SCORE_MAXIMO);
    }
    
    /**
     * Determina a recomendação baseada no score e outros fatores
     */
    private String determinarRecomendacao(double score, double rendaTotal, double valorMensal) {
        if (score >= 80) {
            return "APROVADO - Cliente com excelente perfil financeiro";
        } else if (score >= 60) {
            return "APROVADO - Cliente com bom perfil financeiro";
        } else if (score >= 40) {
            return "ANÁLISE MANUAL - Cliente com perfil regular, requer análise adicional";
        } else {
            return "REJEITADO - Cliente com perfil financeiro insuficiente";
        }
    }
    
    /**
     * Gera parecer detalhado da análise
     */
    private String gerarParecerDetalhado(Cliente cliente, double rendaTotal, double valorMensal, 
                                        double score, String recomendacao) {
        StringBuilder parecer = new StringBuilder();
        
        parecer.append("ANÁLISE FINANCEIRA - PEDIDO DE ALUGUEL\n");
        parecer.append("=====================================\n\n");
        
        parecer.append("DADOS DO CLIENTE:\n");
        parecer.append("Nome: ").append(cliente.getNome()).append("\n");
        parecer.append("CPF: ").append(cliente.getCpf()).append("\n");
        parecer.append("Profissão: ").append(cliente.getProfissao()).append("\n\n");
        
        parecer.append("ANÁLISE FINANCEIRA:\n");
        parecer.append("Renda Total Declarada: R$ ").append(String.format("%.2f", rendaTotal)).append("\n");
        parecer.append("Valor Mensal do Aluguel: R$ ").append(String.format("%.2f", valorMensal)).append("\n");
        parecer.append("Percentual da Renda: ").append(String.format("%.1f", (valorMensal/rendaTotal)*100)).append("%\n");
        parecer.append("Score de Aprovação: ").append(String.format("%.1f", score)).append("/100\n\n");
        
        parecer.append("RECOMENDAÇÃO: ").append(recomendacao).append("\n\n");
        
        parecer.append("OBSERVAÇÕES:\n");
        if (score >= 60) {
            parecer.append("- Cliente apresenta perfil financeiro adequado\n");
            parecer.append("- Renda suficiente para cobertura do aluguel\n");
            parecer.append("- Baixo risco de inadimplência\n");
        } else {
            parecer.append("- Necessário análise adicional\n");
            parecer.append("- Considerar garantias adicionais\n");
            parecer.append("- Monitoramento mais rigoroso recomendado\n");
        }
        
        return parecer.toString();
    }
    
    /**
     * Calcula capacidade de pagamento do cliente
     */
    public Map<String, Object> calcularCapacidadePagamento(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        double rendaTotal = cliente.getRendaTotal();
        double rendaDisponivel = rendaTotal * RENDA_MINIMA_MULTIPLICADOR;
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("rendaTotal", rendaTotal);
        resultado.put("rendaDisponivel", rendaDisponivel);
        resultado.put("valorMaximoAluguel", rendaDisponivel);
        
        return resultado;
    }
    
    /**
     * Verifica se cliente pode arcar com determinado valor
     */
    public boolean verificarCapacidadePagamento(Long clienteId, double valorAluguel) {
        Map<String, Object> capacidade = calcularCapacidadePagamento(clienteId);
        double valorMaximo = (Double) capacidade.get("valorMaximoAluguel");
        
        return valorAluguel <= valorMaximo;
    }
}
