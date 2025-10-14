package org.example.padroesestruturais.bridge.pagamento;

/**
 * Abstração do padrão Bridge para Pagamentos
 * Contém uma referência para a implementação (IMetodoPagamento)
 * e define operações de alto nível
 */
public abstract class Pagamento {
    protected IMetodoPagamento metodoPagamento;
    protected double valor;
    protected String descricao;

    public Pagamento(IMetodoPagamento metodoPagamento, double valor, String descricao) {
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
        this.descricao = descricao;
    }

    /**
     * Método abstrato que será refinado pelas subclasses
     */
    public abstract boolean efetuarPagamento();

    /**
     * Calcula o valor final com taxas
     */
    protected double calcularValorComTaxa() {
        return valor * (1 + metodoPagamento.getTaxa());
    }

    /**
     * Exibe informações do pagamento
     */
    protected void exibirInformacoes() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("PROCESSANDO PAGAMENTO");
        System.out.println("Descrição: " + descricao);
        System.out.printf("Valor: R$ %.2f\n", valor);
        System.out.println("Método: " + metodoPagamento.getNome());
        System.out.println("═══════════════════════════════════════════");
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public IMetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }
}
