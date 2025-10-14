package org.example.padroesestruturais.bridge.pagamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Implementação Concreta - Pagamento via Boleto Bancário
 */
public class PagamentoBoleto implements IMetodoPagamento {

    @Override
    public boolean processar(double valor, int parcelas) {
        System.out.printf("  [BOLETO] Processando pagamento de R$ %.2f\n", valor);

        if (parcelas > 1) {
            System.out.printf("  [BOLETO] Gerando %d boletos para pagamento parcelado\n", parcelas);
            double valorParcela = valor / parcelas;

            for (int i = 1; i <= parcelas; i++) {
                LocalDate vencimento = LocalDate.now().plusMonths(i);
                String codigoBarras = gerarCodigoBarras(i);
                System.out.printf("  [BOLETO] Parcela %d/%d - Vencimento: %s - R$ %.2f\n",
                    i, parcelas, vencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), valorParcela);
                System.out.printf("  [BOLETO] Código: %s\n", codigoBarras);
            }
        } else {
            LocalDate vencimento = LocalDate.now().plusDays(3);
            String codigoBarras = gerarCodigoBarras(1);
            System.out.printf("  [BOLETO] Vencimento: %s\n", vencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("  [BOLETO] Código de barras: %s\n", codigoBarras);
        }

        System.out.println("  [BOLETO] ✓ Boleto(s) gerado(s) com sucesso!");
        return true;
    }

    @Override
    public String getNome() {
        return "Boleto";
    }

    @Override
    public double getTaxa() {
        return 0.015; // Taxa de 1.5%
    }

    private String gerarCodigoBarras(int parcela) {
        return String.format("34191.79001 01043.510047 91020.150008 %d 99380000%05d",
            parcela, (int)(Math.random() * 100000));
    }
}
