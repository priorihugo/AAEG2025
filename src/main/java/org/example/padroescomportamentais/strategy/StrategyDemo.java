package org.example.padroescomportamentais.strategy;

import org.example.padroescomportamentais.strategy.strategies.*;

/**
 * Demonstração do Padrão Strategy
 *
 * Mostra como diferentes estratégias de desconto podem ser aplicadas
 * ao mesmo cálculo de preço, permitindo trocar o algoritmo em tempo de execução.
 */
public class StrategyDemo {

    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║   DEMONSTRAÇÃO DO PADRÃO STRATEGY               ║");
        System.out.println("║   Sistema de Gestão de Oficina Mecânica         ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
        System.out.println();

        double valorServico = 1000.0;

        // Cenário 1: Cliente Regular (sem desconto)
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("CENÁRIO 1: Cliente Regular");
        System.out.println("═══════════════════════════════════════════════════");
        CalculadoraPreco calculadora = new CalculadoraPreco(new SemDescontoStrategy());
        calculadora.exibirDetalhamento(valorServico);
        System.out.println();

        // Cenário 2: Cliente VIP (15% desconto)
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("CENÁRIO 2: Cliente VIP");
        System.out.println("═══════════════════════════════════════════════════");
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());
        calculadora.exibirDetalhamento(valorServico);
        System.out.println();

        // Cenário 3: Desconto por Volume - 2 serviços (não aplica)
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("CENÁRIO 3: Cliente com 2 serviços (não aplica desconto)");
        System.out.println("═══════════════════════════════════════════════════");
        calculadora.setDescontoStrategy(new DescontoVolumeStrategy(2));
        calculadora.exibirDetalhamento(valorServico);
        System.out.println();

        // Cenário 4: Desconto por Volume - 5 serviços (aplica 10%)
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("CENÁRIO 4: Cliente com 5 serviços (10% desconto)");
        System.out.println("═══════════════════════════════════════════════════");
        calculadora.setDescontoStrategy(new DescontoVolumeStrategy(5));
        calculadora.exibirDetalhamento(valorServico);
        System.out.println();

        // Cenário 5: Promoção Black Friday (20% desconto)
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("CENÁRIO 5: Promoção Black Friday");
        System.out.println("═══════════════════════════════════════════════════");
        calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(20.0, "Black Friday"));
        calculadora.exibirDetalhamento(valorServico);
        System.out.println();

        // Cenário 6: Promoção Inauguração (30% desconto)
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("CENÁRIO 6: Promoção Especial de Inauguração");
        System.out.println("═══════════════════════════════════════════════════");
        calculadora.setDescontoStrategy(new DescontoPromocionalStrategy(30.0, "Inauguração"));
        calculadora.exibirDetalhamento(valorServico);
        System.out.println();

        // Demonstração de múltiplos valores
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("COMPARATIVO: Cliente VIP em diferentes valores");
        System.out.println("═══════════════════════════════════════════════════");
        calculadora.setDescontoStrategy(new DescontoVIPStrategy());

        double[] valores = {500.0, 1000.0, 1500.0, 2000.0};
        System.out.println("Estratégia: " + calculadora.getDescricaoEstrategia());
        System.out.println();

        for (double valor : valores) {
            double desconto = calculadora.calcularDesconto(valor);
            double precoFinal = calculadora.calcularPrecoFinal(valor);
            System.out.printf("Valor Original: R$ %8.2f | Desconto: R$ %7.2f | Total: R$ %8.2f%n",
                valor, desconto, precoFinal);
        }

        System.out.println();
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║   BENEFÍCIOS DO PADRÃO STRATEGY                 ║");
        System.out.println("╠═════════════════════════════════════════════════╣");
        System.out.println("║ ✓ Algoritmos intercambiáveis                    ║");
        System.out.println("║ ✓ Fácil adicionar novos tipos de desconto       ║");
        System.out.println("║ ✓ Elimina condicionais complexas (if/else)      ║");
        System.out.println("║ ✓ Princípio Open/Closed aplicado                ║");
        System.out.println("║ ✓ Cada estratégia tem responsabilidade única    ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
    }
}
