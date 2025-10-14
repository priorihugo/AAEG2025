package org.example.padroesestruturais.bridge.pagamento;

/**
 * Abstração Refinada - Pagamento à Vista
 */
public class PagamentoAVista extends Pagamento {

    private double desconto;

    public PagamentoAVista(IMetodoPagamento metodoPagamento, double valor, String descricao) {
        super(metodoPagamento, valor, descricao);
        this.desconto = calcularDesconto();
    }

    @Override
    public boolean efetuarPagamento() {
        exibirInformacoes();

        double valorComDesconto = valor - (valor * desconto);
        double valorFinal = valorComDesconto * (1 + metodoPagamento.getTaxa());

        if (desconto > 0) {
            System.out.printf("Desconto à vista: %.1f%%\n", desconto * 100);
            System.out.printf("Valor com desconto: R$ %.2f\n", valorComDesconto);
        }

        if (metodoPagamento.getTaxa() > 0) {
            System.out.printf("Valor final (com taxa): R$ %.2f\n", valorFinal);
        }

        System.out.println();
        return metodoPagamento.processar(valorComDesconto, 1);
    }

    /**
     * Calcula desconto baseado no método de pagamento
     * Dinheiro e PIX têm desconto de 5%
     */
    private double calcularDesconto() {
        if (metodoPagamento.getNome().equals("Dinheiro") ||
            metodoPagamento.getNome().equals("PIX")) {
            return 0.05; // 5% de desconto
        }
        return 0.0;
    }

    public double getDesconto() {
        return desconto;
    }
}
