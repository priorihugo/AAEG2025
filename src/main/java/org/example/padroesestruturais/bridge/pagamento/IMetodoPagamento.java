package org.example.padroesestruturais.bridge.pagamento;

/**
 * Interface de Implementação do padrão Bridge para Pagamentos
 * Define os métodos que todos os métodos de pagamento concretos devem implementar
 */
public interface IMetodoPagamento {
    /**
     * Processa um pagamento usando o método específico
     * @param valor Valor a ser pago
     * @param parcelas Número de parcelas (1 para à vista)
     * @return true se o pagamento foi processado com sucesso
     */
    boolean processar(double valor, int parcelas);

    /**
     * Retorna o nome do método de pagamento
     * @return Nome do método (ex: "Dinheiro", "Cartão", "PIX")
     */
    String getNome();

    /**
     * Retorna a taxa adicional do método de pagamento
     * @return Taxa em percentual (ex: 0.03 para 3%)
     */
    double getTaxa();
}
