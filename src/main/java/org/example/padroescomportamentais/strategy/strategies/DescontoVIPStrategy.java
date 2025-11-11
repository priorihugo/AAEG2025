package org.example.padroescomportamentais.strategy.strategies;

import org.example.padroescomportamentais.strategy.DescontoStrategy;

/**
 * Estratégia de desconto para clientes VIP - 15% de desconto.
 */
public class DescontoVIPStrategy implements DescontoStrategy {

    private static final double PERCENTUAL_DESCONTO = 15.0;

    @Override
    public double calcularDesconto(double valorOriginal) {
        return valorOriginal * (PERCENTUAL_DESCONTO / 100.0);
    }

    @Override
    public String getDescricao() {
        return String.format("Cliente VIP - %.0f%% de desconto", PERCENTUAL_DESCONTO);
    }
}
