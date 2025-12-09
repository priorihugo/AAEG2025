package org.example.padroescomportamentais.memento;

/**
 * Representa um item individual do orçamento (peça ou serviço)
 *
 * Classe imutável para garantir integridade dos snapshots no padrão Memento
 */
public class ItemOrcamento {

    private final String descricao;
    private final int quantidade;
    private final double valorUnitario;

    public ItemOrcamento(String descricao, int quantidade, double valorUnitario) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("Valor unitário não pode ser negativo");
        }

        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getValorTotal() {
        return quantidade * valorUnitario;
    }

    @Override
    public String toString() {
        return String.format("%s (x%d) - R$ %.2f", descricao, quantidade, getValorTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemOrcamento that = (ItemOrcamento) o;

        if (quantidade != that.quantidade) return false;
        if (Double.compare(that.valorUnitario, valorUnitario) != 0) return false;
        return descricao.equals(that.descricao);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = descricao.hashCode();
        result = 31 * result + quantidade;
        temp = Double.doubleToLongBits(valorUnitario);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
