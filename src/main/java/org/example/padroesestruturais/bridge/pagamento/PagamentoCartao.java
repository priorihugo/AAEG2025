package org.example.padroesestruturais.bridge.pagamento;

/**
 * Implementação Concreta - Pagamento com Cartão
 */
public class PagamentoCartao implements IMetodoPagamento {

    @Override
    public boolean processar(double valor, int parcelas) {
        System.out.printf("  [CARTÃO] Processando pagamento de R$ %.2f em %dx\n", valor, parcelas);

        double valorComTaxa = valor * (1 + getTaxa());
        double valorParcela = valorComTaxa / parcelas;

        System.out.printf("  [CARTÃO] Taxa da operadora: %.1f%%\n", getTaxa() * 100);
        System.out.printf("  [CARTÃO] Valor total com taxa: R$ %.2f\n", valorComTaxa);

        if (parcelas > 1) {
            System.out.printf("  [CARTÃO] Valor por parcela: R$ %.2f\n", valorParcela);
        }

        System.out.println("  [CARTÃO] ✓ Pagamento autorizado!");
        return true;
    }

    @Override
    public String getNome() {
        return "Cartão";
    }

    @Override
    public double getTaxa() {
        return 0.029; // Taxa de 2.9%
    }
}
