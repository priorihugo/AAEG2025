package org.example.padroescomportamentais.strategy.strategies;

import org.example.padroescomportamentais.strategy.DescontoStrategy;

/**
 * Estratégia de desconto promocional com percentual configurável.
 * Ideal para campanhas sazonais como Black Friday, Natal, etc.
 */
public class DescontoPromocionalStrategy implements DescontoStrategy {

    private final double percentualDesconto;
    private final String nomeCampanha;

    /**
     * Construtor que configura o desconto promocional.
     *
     * @param percentualDesconto percentual de desconto (ex: 20.0 para 20%)
     * @param nomeCampanha nome da campanha promocional
     */
    public DescontoPromocionalStrategy(double percentualDesconto, String nomeCampanha) {
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("Percentual de desconto deve estar entre 0 e 100");
        }
        this.percentualDesconto = percentualDesconto;
        this.nomeCampanha = nomeCampanha;
    }

    @Override
    public double calcularDesconto(double valorOriginal) {
        return valorOriginal * (percentualDesconto / 100.0);
    }

    @Override
    public String getDescricao() {
        return String.format("Promoção %s - %.0f%% de desconto",
            nomeCampanha, percentualDesconto);
    }
}
