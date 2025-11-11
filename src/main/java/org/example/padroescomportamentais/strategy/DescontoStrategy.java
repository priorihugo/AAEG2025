package org.example.padroescomportamentais.strategy;

/**
 * Padrão Strategy - Interface Strategy
 *
 * Define o contrato para diferentes estratégias de cálculo de desconto.
 * Permite que algoritmos de desconto sejam intercambiáveis.
 */
public interface DescontoStrategy {

    /**
     * Calcula o valor do desconto baseado no valor original.
     *
     * @param valorOriginal o valor antes do desconto
     * @return o valor do desconto a ser aplicado
     */
    double calcularDesconto(double valorOriginal);

    /**
     * Retorna a descrição da estratégia de desconto.
     *
     * @return descrição da estratégia
     */
    String getDescricao();
}
