package org.example.padroescomportamentais.strategy.strategies;

import org.example.padroescomportamentais.strategy.DescontoStrategy;

/**
 * Estratégia de desconto para clientes regulares - sem desconto aplicado.
 */
public class SemDescontoStrategy implements DescontoStrategy {

    @Override
    public double calcularDesconto(double valorOriginal) {
        return 0.0;
    }

    @Override
    public String getDescricao() {
        return "Cliente Regular - Sem desconto";
    }
}
