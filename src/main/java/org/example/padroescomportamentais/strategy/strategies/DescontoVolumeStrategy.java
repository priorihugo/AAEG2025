package org.example.padroescomportamentais.strategy.strategies;

import org.example.padroescomportamentais.strategy.DescontoStrategy;

/**
 * Estratégia de desconto por volume de serviços.
 * Aplica 10% de desconto se o cliente tiver mais de 3 serviços.
 */
public class DescontoVolumeStrategy implements DescontoStrategy {

    private static final double PERCENTUAL_DESCONTO = 10.0;
    private static final int QUANTIDADE_MINIMA = 3;

    private final int quantidadeServicos;

    /**
     * Construtor que recebe a quantidade de serviços do cliente.
     *
     * @param quantidadeServicos quantidade de serviços realizados
     */
    public DescontoVolumeStrategy(int quantidadeServicos) {
        this.quantidadeServicos = quantidadeServicos;
    }

    @Override
    public double calcularDesconto(double valorOriginal) {
        if (quantidadeServicos > QUANTIDADE_MINIMA) {
            return valorOriginal * (PERCENTUAL_DESCONTO / 100.0);
        }
        return 0.0;
    }

    @Override
    public String getDescricao() {
        if (quantidadeServicos > QUANTIDADE_MINIMA) {
            return String.format("Desconto por Volume - %.0f%% (%d serviços)",
                PERCENTUAL_DESCONTO, quantidadeServicos);
        }
        return String.format("Desconto por Volume - Não aplicável (%d serviços, mínimo %d)",
            quantidadeServicos, QUANTIDADE_MINIMA + 1);
    }
}
