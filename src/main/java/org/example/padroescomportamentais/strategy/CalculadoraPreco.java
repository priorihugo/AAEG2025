package org.example.padroescomportamentais.strategy;

/**
 * Padrão Strategy - Contexto
 *
 * Classe que utiliza estratégias de desconto para calcular o preço final.
 * Permite trocar a estratégia de desconto em tempo de execução.
 */
public class CalculadoraPreco {

    private DescontoStrategy descontoStrategy;

    /**
     * Construtor que recebe a estratégia de desconto via injeção de dependências.
     *
     * @param descontoStrategy estratégia de desconto a ser utilizada
     */
    public CalculadoraPreco(DescontoStrategy descontoStrategy) {
        if (descontoStrategy == null) {
            throw new IllegalArgumentException("Estratégia de desconto não pode ser null");
        }
        this.descontoStrategy = descontoStrategy;
    }

    /**
     * Permite trocar a estratégia de desconto em tempo de execução.
     *
     * @param descontoStrategy nova estratégia de desconto
     */
    public void setDescontoStrategy(DescontoStrategy descontoStrategy) {
        if (descontoStrategy == null) {
            throw new IllegalArgumentException("Estratégia de desconto não pode ser null");
        }
        this.descontoStrategy = descontoStrategy;
    }

    /**
     * Calcula o valor do desconto baseado na estratégia atual.
     *
     * @param valorOriginal valor original do serviço
     * @return valor do desconto
     */
    public double calcularDesconto(double valorOriginal) {
        return descontoStrategy.calcularDesconto(valorOriginal);
    }

    /**
     * Calcula o preço final após aplicar o desconto.
     *
     * @param valorOriginal valor original do serviço
     * @return preço final com desconto aplicado
     */
    public double calcularPrecoFinal(double valorOriginal) {
        double desconto = calcularDesconto(valorOriginal);
        return valorOriginal - desconto;
    }

    /**
     * Exibe o detalhamento completo do cálculo de preço.
     *
     * @param valorOriginal valor original do serviço
     */
    public void exibirDetalhamento(double valorOriginal) {
        double desconto = calcularDesconto(valorOriginal);
        double precoFinal = valorOriginal - desconto;
        double percentual = (desconto / valorOriginal) * 100;

        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│       DETALHAMENTO DO ORÇAMENTO             │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf("│ Estratégia: %-31s │%n", descontoStrategy.getDescricao());
        System.out.printf("│ Valor Original: R$ %24.2f │%n", valorOriginal);
        System.out.printf("│ Desconto (%.0f%%): R$ %24.2f │%n", percentual, desconto);
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf("│ TOTAL A PAGAR: R$ %25.2f │%n", precoFinal);
        System.out.println("└─────────────────────────────────────────────┘");
    }

    /**
     * Retorna a descrição da estratégia atual.
     *
     * @return descrição da estratégia
     */
    public String getDescricaoEstrategia() {
        return descontoStrategy.getDescricao();
    }
}
