package org.example.padroesestruturais.bridge.pagamento;

import java.util.UUID;

/**
 * Implementação Concreta - Pagamento via PIX
 */
public class PagamentoPIX implements IMetodoPagamento {

    @Override
    public boolean processar(double valor, int parcelas) {
        System.out.printf("  [PIX] Processando pagamento de R$ %.2f\n", valor);

        if (parcelas > 1) {
            System.out.println("  [PIX] Aviso: PIX não suporta parcelamento. Processando à vista.");
        }

        String chave = gerarChavePIX();
        System.out.println("  [PIX] Chave gerada: " + chave);
        System.out.println("  [PIX] Aguardando confirmação...");
        System.out.println("  [PIX] ✓ Pagamento confirmado instantaneamente!");

        return true;
    }

    @Override
    public String getNome() {
        return "PIX";
    }

    @Override
    public double getTaxa() {
        return 0.0; // Sem taxa
    }

    private String gerarChavePIX() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
