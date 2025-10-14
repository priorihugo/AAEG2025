package org.example.padroesestruturais.bridge.pagamento;

/**
 * Abstração Refinada - Pagamento Parcelado
 */
public class PagamentoParcelado extends Pagamento {

    private int numeroParcelas;
    private static final int MAX_PARCELAS_SEM_JUROS = 3;
    private static final double TAXA_JUROS = 0.02; // 2% ao mês

    public PagamentoParcelado(IMetodoPagamento metodoPagamento, double valor, String descricao, int numeroParcelas) {
        super(metodoPagamento, valor, descricao);
        this.numeroParcelas = validarParcelas(numeroParcelas);
    }

    @Override
    public boolean efetuarPagamento() {
        exibirInformacoes();

        System.out.printf("Número de parcelas: %dx\n", numeroParcelas);

        double valorComJuros = calcularValorComJuros();
        double valorFinal = valorComJuros * (1 + metodoPagamento.getTaxa());
        double valorParcela = valorFinal / numeroParcelas;

        if (numeroParcelas > MAX_PARCELAS_SEM_JUROS) {
            System.out.printf("Juros aplicados: %.1f%% ao mês\n", TAXA_JUROS * 100);
            System.out.printf("Valor com juros: R$ %.2f\n", valorComJuros);
        } else {
            System.out.println("Parcelamento sem juros!");
        }

        if (metodoPagamento.getTaxa() > 0) {
            System.out.printf("Valor final (com taxa): R$ %.2f\n", valorFinal);
        }

        System.out.printf("Valor da parcela: R$ %.2f\n", valorParcela);
        System.out.println();

        return metodoPagamento.processar(valorComJuros, numeroParcelas);
    }

    /**
     * Calcula valor com juros se parcelar acima do máximo sem juros
     */
    private double calcularValorComJuros() {
        if (numeroParcelas <= MAX_PARCELAS_SEM_JUROS) {
            return valor;
        }

        // Juros compostos
        int mesesComJuros = numeroParcelas - MAX_PARCELAS_SEM_JUROS;
        return valor * Math.pow(1 + TAXA_JUROS, mesesComJuros);
    }

    /**
     * Valida e ajusta o número de parcelas baseado no método de pagamento
     */
    private int validarParcelas(int parcelas) {
        String metodo = metodoPagamento.getNome();

        if (metodo.equals("Dinheiro") || metodo.equals("PIX")) {
            System.out.println("  [AVISO] " + metodo + " não suporta parcelamento. Ajustando para 1x.");
            return 1;
        }

        if (metodo.equals("Boleto") && parcelas > 12) {
            System.out.println("  [AVISO] Boleto permite no máximo 12 parcelas. Ajustando.");
            return 12;
        }

        if (metodo.equals("Cartão") && parcelas > 18) {
            System.out.println("  [AVISO] Cartão permite no máximo 18 parcelas. Ajustando.");
            return 18;
        }

        return parcelas;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }
}
