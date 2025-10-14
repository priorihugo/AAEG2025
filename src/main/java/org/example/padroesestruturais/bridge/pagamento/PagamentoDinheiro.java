package org.example.padroesestruturais.bridge.pagamento;

/**
 * Implementação Concreta - Pagamento em Dinheiro
 */
public class PagamentoDinheiro implements IMetodoPagamento {

    @Override
    public boolean processar(double valor, int parcelas) {
        System.out.printf("  [DINHEIRO] Processando pagamento de R$ %.2f\n", valor);

        if (parcelas > 1) {
            System.out.println("  [DINHEIRO] Aviso: Dinheiro não suporta parcelamento. Processando à vista.");
        }

        System.out.println("  [DINHEIRO] ✓ Pagamento recebido com sucesso!");
        return true;
    }

    @Override
    public String getNome() {
        return "Dinheiro";
    }

    @Override
    public double getTaxa() {
        return 0.0; // Sem taxa
    }
}
